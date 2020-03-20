package com.balsdon.ratesapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_rate_list.*

class RateListActivity : AppCompatActivity(), RequiresDataBroker, SubscribesToObservers {

    private lateinit var dataBroker: DataBroker

    private val rateListResultObserver = Observer(::updateUI)
    private val rateListAdapter = RateListAdapter(emptyList())

    override fun setDataBroker(dataBroker: DataBroker) {
        this.dataBroker = dataBroker
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, RateListModelFactory(dataBroker))
            .get(RateListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_list)

        rate_list_title.setOnClickListener {
            showCredits()
        }

        currency_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RateListActivity)
            adapter = rateListAdapter
        }
    }

    private fun refresh() {
        viewModel.refresh()
    }

    private fun showError(message: String?) {
        Snackbar
            .make(
                currency_list,
                message ?: getString(R.string.error_empty_list),
                Snackbar.LENGTH_INDEFINITE
            )
            .setAction(R.string.refresh) { refresh() }
            .show()
    }

    private fun updateUI(result: RateListResult) {
        when (result) {
            is RateListResult.Success -> rateListAdapter.update(result.list)
            is RateListResult.Error -> showError(result.message)
            is RateListResult.Empty -> showError(null)
        }
    }

    override fun registerObservers() {
        viewModel.getRateList().observe(this, rateListResultObserver)
    }

    override fun unregisterObservers() {
        viewModel.getRateList().removeObserver(rateListResultObserver)
    }

    private fun showCredits() {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_credits_title)
            .setMessage(
                HtmlCompat.fromHtml(
                    getString(R.string.app_credits_message),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
}
