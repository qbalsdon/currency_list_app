package com.balsdon.ratesapp.rateItem

import com.balsdon.ratesapp.model.RateItem
import java.math.BigDecimal

class RateItemPresenter(private val viewable: RateItemViewable) {
    companion object {
        private const val DECIMAL_PLACES = 2
        private var multiplier: Double = 1.0
    }

    private var baseRate: Double = 1.0

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
        viewable.setCurrencyCode(rateItem.currencyCode)
        viewable.setIcon(rateItem.getFlagIconResource())
        viewable.setCurrencyName(rateItem.getCurrencyNameResource())
    }

    fun updateUserEntry(text: String) {
        multiplier = text.toDoubleOrNull() ?: 1.0
        viewable.getMultiplierChanged().invoke()
    }
}
