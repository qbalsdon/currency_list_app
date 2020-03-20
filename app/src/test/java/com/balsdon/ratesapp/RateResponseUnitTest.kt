package com.balsdon.ratesapp

import com.balsdon.ratesapp.model.RateItem
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class RateResponseUnitTest {
    @Test
    fun rateItemUsesCurrencyCodeToGetImageIntResource() {
        //given
        RateItem.countryResourceResolver = mockk(relaxed = true)

        //when
        val list = TestData.testRateResponse.toRateItemList()

        //then
        assert(list.size == TestData.testRateResponse.rates.size + 1)
        Assert.assertEquals(list[0].currencyCode, "AUD")
    }
}
