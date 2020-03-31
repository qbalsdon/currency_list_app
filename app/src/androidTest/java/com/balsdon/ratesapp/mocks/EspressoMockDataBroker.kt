package com.balsdon.ratesapp.mocks

import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateFetcher
import com.balsdon.ratesapp.dataBroker.RateListResult
import java.util.concurrent.atomic.AtomicBoolean

class EspressoMockDataBroker(private val rateFetcher: RateFetcher) : DataBroker {
    private var hasError: AtomicBoolean = AtomicBoolean(false)

    private lateinit var currencyCode: String
    private lateinit var updateDelegate: (RateListResult) -> Unit

    override fun subscribeToRates(update: (RateListResult) -> Unit) {
        subscribeToRates("EUR", update)
    }

    override fun subscribeToRates(currencyCode: String, update: (RateListResult) -> Unit) {
        this.currencyCode = currencyCode
        this.updateDelegate = update
        next()
    }

    fun next() = rateFetcher.fetchRates(currencyCode, updateDelegate)

    override fun unsubscribe() {
        //ok
    }
}