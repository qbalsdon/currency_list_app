package com.balsdon.ratesapp

import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.RateItemPresenter
import com.balsdon.ratesapp.rateItem.RateItemView
import com.balsdon.ratesapp.rateItem.flagResource.CountryResourceResolver
import io.mockk.*
import org.junit.Test

import org.junit.Before

class RateItemPresenterUnitTest {

    private val mockedResourceResolver = mockk<CountryResourceResolver>()
    private val RATE_ITEM = RateItem("ZAR", 2.5)
    private val IMAGE_CODE = 100
    private val STRING_CODE = 101


    private val mockedRateItemView = mockk<RateItemView>()
    private val presenter = RateItemPresenter(mockedRateItemView)

    @Before
    fun before() {
        every { mockedResourceResolver.getFlagIconResourceFromCode(any()) } returns IMAGE_CODE
        every { mockedResourceResolver.getCurrencyStringResourceFromCode(any()) } returns STRING_CODE
        RateItem.countryResourceResolver = mockedResourceResolver

        every { mockedRateItemView.setCurrencyCode(RATE_ITEM.currencyCode) } just runs
        every { mockedRateItemView.setCurrencyRate(any()) } just runs
        every { mockedRateItemView.setIcon(any()) } just runs
        every { mockedRateItemView.setCurrencyName(any()) } just runs
    }

    @Test
    fun rateItemPresenterDisplaysCurrencyCodeInView() {
        //given
        //when
        presenter.setRate(RATE_ITEM, mockk(relaxed = true))

        //then
        io.mockk.verify(exactly = 1) {
            mockedRateItemView.setCurrencyCode(RATE_ITEM.currencyCode)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCurrencyNameInView() {
        //given
        //when
        presenter.setRate(RATE_ITEM, mockk(relaxed = true))

        //then
        io.mockk.verify(exactly = 1) {
            mockedRateItemView.setCurrencyName(STRING_CODE)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCurrencyIconInView() {
        //given
        //when
        presenter.setRate(RATE_ITEM, mockk(relaxed = true))

        //then
        io.mockk.verify(exactly = 1) {
            mockedRateItemView.setIcon(IMAGE_CODE)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesExactly() {
        //given
        val expected = "11.25"
        //when
        presenter.setRate(RATE_ITEM, 4.5)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }

    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesZero() {
        //given
        val expected = "19.00"

        //when
        presenter.setRate(RATE_ITEM, 7.6)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }

    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesRoundUp5() {
        //given
        val expected = "6.13" //6.125 is actual result

        //when
        presenter.setRate(RATE_ITEM, 2.45)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesRoundDown() {
        //given
        val expected = "6.14" //6.14175 - Round down

        //when
        presenter.setRate(RATE_ITEM, 2.4567)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesRoundUpGreaterThan5() {
        //given
        val expected = "4.24" //4.2375 is actual result

        //when
        presenter.setRate(RATE_ITEM, 1.695)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }
    }
}
