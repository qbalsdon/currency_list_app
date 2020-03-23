package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.dataBroker.RateListResult

interface ApiService {
    fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit)
}