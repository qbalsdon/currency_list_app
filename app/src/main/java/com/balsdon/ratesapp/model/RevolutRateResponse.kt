package com.balsdon.ratesapp.model

data class RevolutRateResponse(val baseCurrency: String, val rates: Map<String, Double>) :
    EnvironmentResponseMapper {

    override fun toRateResponse() =
        RateResponse(baseCurrency, rates.map { RateItem(it.key, it.value) })

    override fun isEmpty() = rates.isEmpty()
}