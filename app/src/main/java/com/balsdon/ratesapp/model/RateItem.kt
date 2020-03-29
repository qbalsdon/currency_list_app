package com.balsdon.ratesapp.model

import com.balsdon.ratesapp.rateItem.flagResource.CountryResourceResolver
import java.io.Serializable

data class RateItem(val currencyCode: String, val rate: Double = 1.0) : Serializable {
    companion object {
        lateinit var countryResourceResolver: CountryResourceResolver
    }

    fun getFlagIconResource() : Int = countryResourceResolver.getFlagIconResourceFromCode(currencyCode)
    fun getCurrencyNameResource() : Int = countryResourceResolver.getCurrencyStringResourceFromCode(currencyCode)
}