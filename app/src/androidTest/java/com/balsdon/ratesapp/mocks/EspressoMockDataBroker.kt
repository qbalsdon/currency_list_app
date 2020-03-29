package com.balsdon.ratesapp.mocks

import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.service.ApiService
import java.util.concurrent.atomic.AtomicBoolean

class EspressoMockDataBroker(private val service: ApiService) : DataBroker {
    private var hasError: AtomicBoolean = AtomicBoolean(false)

    private lateinit var currencyCode: String
    private lateinit var updateDelegate: (RateListResult) -> Unit

    override fun subscribeToRates(currencyCode: String, update: (RateListResult) -> Unit) {
        this.currencyCode = currencyCode
        this.updateDelegate = update
        next()
    }

    fun next() = service.fetchRates(currencyCode, updateDelegate)

    override fun unsubscribe() {
        //ok
    }
}