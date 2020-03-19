package com.balsdon.ratesapp.rateItem

interface RateItemViewable {
    fun setCurrencyCode(code: String)
    fun setCurrencyRate(currencyRate: String)
    fun setCurrencyName(stringResourceInt: Int)
    fun setIcon(drawableResourceInt: Int)
}