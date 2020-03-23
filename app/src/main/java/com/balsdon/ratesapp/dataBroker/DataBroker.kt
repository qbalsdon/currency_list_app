package com.balsdon.ratesapp.dataBroker

interface DataBroker {
    fun subscribeToRates(currencyCode: String, update: (RateListResult) -> Unit)
    fun unsubscribe()
}