package com.balsdon.ratesapp.rateItem

import com.balsdon.ratesapp.model.RateItem
import java.math.BigDecimal

class RateItemPresenter(private val viewable: RateItemViewable) {
    companion object {
        private const val DECIMAL_PLACES = 2
    }

    private fun scaledRate(rate: Double, multiplier: Double) : String {
        val rateTimesMultiplier = rate * multiplier
        val scaledValue = BigDecimal(rateTimesMultiplier).setScale(DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP)
        return scaledValue.toString()
    }

    fun setRate(rateItem: RateItem, multiplier: Double) {
        viewable.setCurrencyCode(rateItem.currencyCode)

        viewable.setCurrencyRate(scaledRate(rateItem.rate, multiplier))

        viewable.setIcon(rateItem.getFlagIconResource())
        viewable.setCurrencyName(rateItem.getCurrencyNameResource())
    }

    fun rateChanged() {
        throw NotImplementedError("Class ${this::class.java.canonicalName} has no method implementation rateChanged() ")
    }
}
