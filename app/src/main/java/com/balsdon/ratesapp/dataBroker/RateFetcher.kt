package com.balsdon.ratesapp.dataBroker

interface RateFetcher {
    fun fetchRates(update: (RateListResult) -> Unit)
    fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit)
}