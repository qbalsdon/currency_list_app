package com.balsdon.ratesapp

import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.flagResource.CountryResourceResolver
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.math.BigDecimal

class RateItemUnitTest {
    private val mockedResourceResolver = mockk<CountryResourceResolver>()
    private val currencyCode = "ZAR"
    private val expectedImage = -100
    private val expectedString = 101

    @Before
    fun setup() {
        every { mockedResourceResolver.getFlagIconResourceFromCode(currencyCode) } returns expectedImage
        every { mockedResourceResolver.getCurrencyStringResourceFromCode(currencyCode) } returns expectedString
        RateItem.countryResourceResolver = mockedResourceResolver
    }

    @Test
    fun rateItemUsesCurrencyCodeToGetImageIntResource() {
        //given
        val rateItem = RateItem(currencyCode, mockk(relaxed = true))

        //when
        val actual = rateItem.getFlagIconResource()

        //then
        assertEquals(expectedImage, actual)
        verify(exactly = 1) { mockedResourceResolver.getFlagIconResourceFromCode(currencyCode) }
    }

    @Test
    fun rateItemUsesCurrencyCodeToGetStringIntResource() {
        //given
        val rateItem = RateItem(currencyCode, mockk(relaxed = true))

        //when
        val actual = rateItem.getCurrencyNameResource()

        //then
        assertEquals(expectedString, actual)
        verify(exactly = 1) { mockedResourceResolver.getCurrencyStringResourceFromCode(currencyCode) }
    }
}
