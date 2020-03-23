package com.balsdon.ratesapp.model

data class RateResponse(val baseCurrency: String, val rates: Map<String, Double>) {
    fun toRateItemList(): List<RateItem> = rates.map { RateItem(it.key, it.value) }
}
