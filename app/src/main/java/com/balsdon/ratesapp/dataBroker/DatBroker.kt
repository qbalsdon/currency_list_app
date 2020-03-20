package com.balsdon.ratesapp.dataBroker

interface DataBroker {
    fun getRates() : RateListResult
}