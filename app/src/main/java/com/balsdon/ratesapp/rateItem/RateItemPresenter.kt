package com.balsdon.ratesapp.rateItem

import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.flagResource.CountryResourceResolver
import java.math.BigDecimal

class RateItemPresenter(private val viewable: RateItemViewable) {
    companion object {
        private const val DECIMAL_PLACES = 2
        private var multiplier: Double = 1.0

        lateinit var countryResourceResolver: CountryResourceResolver
    }

    private var baseRate: Double = 1.0
    private var data: RateItem = RateItem("", 1.0)

    private fun scaledRate(rate: Double) : String {
        val rateTimesMultiplier = rate * multiplier
        val scaledValue = BigDecimal(rateTimesMultiplier).setScale(DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP)
        return scaledValue.toString()
    }

    fun setRate(rateItem: RateItem) {
        baseRate = rateItem.rate
        viewable.setCurrencyRate(scaledRate(baseRate))
        setCurrency(rateItem)
    }

    fun setCurrency(rateItem: RateItem) {
        data = rateItem
        viewable.setCurrencyCode(rateItem.currencyCode)
        viewable.setIcon(countryResourceResolver.getFlagIconResourceFromCode(rateItem.currencyCode))
        viewable.setCurrencyName(countryResourceResolver.getCurrencyStringResourceFromCode(rateItem.currencyCode))
    }

    fun updateUserEntry(text: String) {
        multiplier = text.toDoubleOrNull() ?: 0.0
        viewable.getMultiplierChanged().invoke()
    }

    fun getRateItem(): RateItem = data
}
