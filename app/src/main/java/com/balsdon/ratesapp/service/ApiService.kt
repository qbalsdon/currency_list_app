package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.dataBroker.RateListResult

interface ApiService {
    fun fetchRates(currencyCode: String = "EUR", update: (RateListResult) -> Unit)
}