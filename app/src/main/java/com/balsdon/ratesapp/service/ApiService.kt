package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.dataBroker.RateListResult

interface ApiService {
    fun fetchRates(update: (RateListResult) -> Unit)
    fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit)
}