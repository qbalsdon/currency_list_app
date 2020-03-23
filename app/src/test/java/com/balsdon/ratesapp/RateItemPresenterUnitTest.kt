package com.balsdon.ratesapp

import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.RateItemPresenter
import com.balsdon.ratesapp.rateItem.RateItemView
import com.balsdon.ratesapp.rateItem.RateItemViewable
import com.balsdon.ratesapp.rateItem.flagResource.CountryResourceResolver
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RateItemPresenterUnitTest {

    private val mockedResourceResolver = mockk<CountryResourceResolver>()
    private val RATE_ITEM = RateItem("ZAR", 2.5)
    private val IMAGE_CODE = 100
    private val STRING_CODE = 101

    private val mockedRateItemView = mockk<RateItemView>(relaxed = true)
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
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyCode(RATE_ITEM.currencyCode)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCurrencyNameInView() {
        //given
        //when
        presenter.setRate(RATE_ITEM)

        //then
        io.mockk.verify(exactly = 1) {
            mockedRateItemView.setCurrencyName(STRING_CODE)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCurrencyIconInView() {
        //given
        //when
        presenter.setRate(RATE_ITEM)

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
        presenter.updateUserEntry("4.5")
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }

    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesTrailingZero() {
        //given
        val expected = "12.50"
        //when
        presenter.updateUserEntry("5.0")
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }

    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesZero() {
        //given
        val expected = "19.00"
        presenter.updateUserEntry("7.6")

        //when
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }

    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesRoundUp5() {
        //given
        val expected = "6.13" //6.125 is actual result
        presenter.updateUserEntry("2.45")
        //when
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesRoundDown() {
        //given
        val expected = "6.14" //6.14175 - Round down
        presenter.updateUserEntry("2.4567")
        //when
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }
    }

    @Test
    fun rateItemPresenterDisplaysCorrectRateInViewTwoDecimalPlacesRoundUpGreaterThan5() {
        //given
        val expected = "4.24" //4.2375 is actual result
        presenter.updateUserEntry("1.695")
        //when
        presenter.setRate(RATE_ITEM)

        //then
        verify(exactly = 1) {
            mockedRateItemView.setCurrencyRate(expected)
        }
    }

    @Test
    fun rateItemPresenterCallsUpdate() {
        //given
        val expectedChanges = 2
        var count = 0
        val stubbedRateItemView = spyk(object: RateItemViewable {
            override fun setCurrencyCode(code: String) {}

            override fun setCurrencyRate(currencyRate: String) {}

            override fun setCurrencyName(stringResourceInt: Int) {}

            override fun setIcon(drawableResourceInt: Int) {}

            override fun getMultiplierChanged(): () -> Unit = {
                count+=1
            }
        })

        val presenter = RateItemPresenter(stubbedRateItemView)
        //when
        presenter.updateUserEntry("2.6")
        presenter.setRate(RATE_ITEM)
        presenter.setRate(RATE_ITEM)
        presenter.setRate(RATE_ITEM)
        presenter.updateUserEntry("5.7")
        //then
        assertEquals(expectedChanges, count)
    }
}
