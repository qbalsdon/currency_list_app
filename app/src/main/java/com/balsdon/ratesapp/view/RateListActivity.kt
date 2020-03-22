package com.balsdon.ratesapp.view

import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.balsdon.ratesapp.*
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_rate_list.*

class RateListActivity : AppCompatActivity(), RequiresDataBroker,
    SubscribesToObservers {

    private lateinit var dataBroker: DataBroker

    private val rateListResultObserver = Observer(::updateUI)
    private val rateListAdapter =
        RateListAdapter(emptyList())

    override fun setDataBroker(dataBroker: DataBroker) {
        this.dataBroker = dataBroker
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this,
                RateListModelFactory(dataBroker)
            )
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

    private fun errorCodeToStringResource(code: RateListResult.ErrorCode) : Int =
        when (code) {
            RateListResult.ErrorCode.SERVER_ERROR -> R.string.error_server
            RateListResult.ErrorCode.TIMEOUT_ERROR -> R.string.error_timeout
            else -> R.string.error_generic
        }

    private fun showError(errorCode: RateListResult.ErrorCode) =
        showMessage(errorCodeToStringResource(errorCode))


    private fun showMessage(messageResource: Int) =
        Snackbar
            .make(
                currency_list,
                messageResource,
                Snackbar.LENGTH_INDEFINITE
            )
            .setAction(R.string.refresh) { refresh() }
            .show()

    private fun updateUI(result: RateListResult) {
        when (result) {
            is RateListResult.Success -> rateListAdapter.update(result.list)
            is RateListResult.Error -> showError(result.errorCode)
            is RateListResult.Empty -> showMessage(R.string.error_empty_list)
        }
    }

    override fun registerObservers() {
        viewModel.getRateList().observe(this, rateListResultObserver)
    }

    override fun unregisterObservers() {
        viewModel.getRateList().removeObserver(rateListResultObserver)
    }

    private fun showCredits() {
        val htmlMessage = HtmlCompat.fromHtml(getString(R.string.app_credits_message), HtmlCompat.FROM_HTML_MODE_LEGACY)

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.app_credits_title)
            .setMessage(htmlMessage)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
        dialog.apply {
            show()
            findViewById<TextView>(android.R.id.message)?.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
