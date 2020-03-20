package com.balsdon.ratesapp.model

data class RateResponse(val baseCurrency: String, val rates: Map<String, Double>) {
    fun toRateItemList(): List<RateItem> = mutableListOf(RateItem(baseCurrency, 1.0))
        .apply {
            addAll(rates.map {
                RateItem(it.key, it.value)
            })
        }
}
