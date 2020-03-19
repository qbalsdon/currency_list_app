package com.balsdon.ratesapp.rateItem.flagResource

interface CountryResourceResolver {
    fun getFlagIconResourceFromCode(code: String) : Int
    fun getCurrencyStringResourceFromCode(code: String) : Int
}
