package com.balsdon.ratesapp.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.view.viewModel.RateListModelFactory
import com.balsdon.ratesapp.view.viewModel.RateListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_rate_list.*

class RateListActivity : AppCompatActivity(), RequiresDataBroker,
    SubscribesToObservers {

    companion object {
        const val PREF_CURRENCY_CODE = "PREF_CURRENCY_CODE"
        const val PREF_CURRENCY_RATE = "PREF_CURRENCY_RATE"
        const val DEFAULT_START_RATE = "1.00"
    }

    private lateinit var dataBroker: DataBroker

    private val viewModel by lazy {
        ViewModelProvider(this, RateListModelFactory(dataBroker))
            .get(RateListViewModel::class.java)
    }

    private val rateListResultObserver = Observer(::updateUI)

    private val rateListAdapter =
        RateListAdapter(emptyList()) { rateItem ->
            rate_list_base.setRate(rateItem)
            viewModel.apply {
                unsubscribe()
                refresh(rateItem)
            }
        }

    override fun setDataBroker(dataBroker: DataBroker) {
        this.dataBroker = dataBroker
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

        rate_list_base.apply {
            enableTextEntry()
            setMultiplierChanged { rateListAdapter.updateMultiplier() }
            setMaxInputLimit(RateListViewModel.MAX_INPUT_LENGTH)
        }
    }

    override fun onResume() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if (prefs.contains(PREF_CURRENCY_CODE) && prefs.contains(PREF_CURRENCY_RATE)) {
            prefs.apply {
                val code = getString(PREF_CURRENCY_CODE, "") ?: ""
                val rate = getFloat(PREF_CURRENCY_RATE, -1f).toDouble()
                RateItem(code, rate).apply {
                    rate_list_base.setCurrencyRate(rate.toString())
                    rate_list_base.setRate(this)
                    viewModel.refresh(this)

                }
            }
        } else {
            rate_list_base.setCurrencyRate(DEFAULT_START_RATE)
            viewModel.refresh()
        }

        super.onResume()
    }

    override fun onPause() {
        val item = rate_list_base.getRateItem()
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .edit()
            .apply {
                putString(PREF_CURRENCY_CODE, item.currencyCode)
                putFloat(PREF_CURRENCY_RATE, item.rate.toFloat())
                apply()
            }
        viewModel.unsubscribe()
        super.onPause()
    }

    private fun errorCodeToStringResource(code: RateListResult.ErrorCode): Int =
        when (code) {
            RateListResult.ErrorCode.SERVER_ERROR -> R.string.error_server
            RateListResult.ErrorCode.TIMEOUT_ERROR -> R.string.error_timeout
            else -> R.string.error_generic
        }

    private fun showError(errorCode: RateListResult.ErrorCode) =
        showMessage(errorCodeToStringResource(errorCode))


    private fun showMessage(messageResource: Int) {
        rate_list_loading_progress.visibility = View.GONE
        Snackbar
            .make(
                currency_list,
                messageResource,
                Snackbar.LENGTH_INDEFINITE
            )
            .setAction(R.string.refresh) {
                if (currency_list.visibility == View.GONE) {
                    rate_list_loading_progress.visibility = View.VISIBLE
                }
                viewModel.refresh()
            }
            .show()
    }

    private fun updateView(result: RateListResult.Success) {

        rate_list_splash_image.visibility = View.GONE
        rate_list_loading_progress.visibility = View.GONE
        rate_list_base.visibility = View.VISIBLE
        currency_list.visibility = View.VISIBLE
        rateListAdapter.updateList(result.response.rates)
        rate_list_base
            .setCurrency(RateItem(result.response.baseCurrency))
    }

    private fun updateUI(result: RateListResult) {
        when (result) {
            is RateListResult.Success -> updateView(result)
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
        val htmlMessage = HtmlCompat.fromHtml(
            getString(R.string.app_credits_message),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.app_credits_title)
            .setMessage(htmlMessage)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
        dialog.apply {
            show()
            findViewById<TextView>(android.R.id.message)?.movementMethod =
                LinkMovementMethod.getInstance()
        }
    }
}
