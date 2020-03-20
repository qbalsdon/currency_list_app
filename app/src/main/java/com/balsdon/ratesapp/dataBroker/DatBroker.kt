package com.balsdon.ratesapp.dataBroker

interface DataBroker {
    fun subscribeToRates(update: (RateListResult) -> Unit)
    fun unsubscribe()
}