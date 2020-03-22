package com.balsdon.ratesapp.offlinemock.dataBroker

import android.os.AsyncTask
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.service.ApiService
import java.util.*

class DebugMockService() : ApiService {
    companion object {
        const val RANDOM_MIN = 0.0
        const val RANDOM_MAX = 10000.0

        val randomGenerator: Random by lazy {
            Random()
        }
    }
    fun randomDouble() : Double =
        RANDOM_MIN + (RANDOM_MAX - RANDOM_MIN) * randomGenerator.nextDouble()

    private fun generateSuccessResult() = RateListResult.Success(
        listOf(
            RateItem("AUD", 1.0),
            RateItem("BGN", randomDouble()),
            RateItem("BRL", randomDouble()),
            RateItem("CAD", randomDouble()),
            RateItem("CHF", randomDouble()),
            RateItem("CNY", randomDouble()),
            RateItem("CZK", randomDouble()),
            RateItem("DKK", randomDouble()),
            RateItem("EUR", randomDouble()),
            RateItem("GBP", randomDouble()),
            RateItem("HKD", randomDouble()),
            RateItem("HRK", randomDouble()),
            RateItem("HUF", randomDouble()),
            RateItem("IDR", randomDouble()),
            RateItem("ILS", randomDouble()),
            RateItem("INR", randomDouble()),
            RateItem("ISK", randomDouble()),
            RateItem("JPY", randomDouble()),
            RateItem("KRW", randomDouble()),
            RateItem("MXN", randomDouble()),
            RateItem("MYR", randomDouble()),
            RateItem("NOK", randomDouble()),
            RateItem("NZD", randomDouble()),
            RateItem("PHP", randomDouble()),
            RateItem("PLN", randomDouble()),
            RateItem("RON", randomDouble()),
            RateItem("RUB", randomDouble()),
            RateItem("SEK", randomDouble()),
            RateItem("SGD", randomDouble()),
            RateItem("THB", randomDouble()),
            RateItem("USD", randomDouble()),
            RateItem("ZAR", randomDouble())
        )
    )

    private val uiResult = RateListResult.Success(
        listOf(
            RateItem("USD", 1183.06),
            RateItem("EUR", 2.7083157236),
            RateItem("SEK", 1.8211164269),
            RateItem("CAD", 2.793121228)
        )
    )

    private val emptyResult = RateListResult.Empty
    private val errorResult = RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR)

    override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
        update.invoke(generateSuccessResult())
    }
}