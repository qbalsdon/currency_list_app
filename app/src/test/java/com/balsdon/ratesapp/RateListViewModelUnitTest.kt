package com.balsdon.ratesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.view.viewModel.RateListViewModel
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TestBroker(private val result: RateListResult) : DataBroker {
    override fun subscribeToRates(update: (RateListResult) -> Unit) {
        update.invoke(result)
    }

    override fun subscribeToRates(currencyCode: String, update: (RateListResult) -> Unit) {
        update.invoke(result)
    }

    override fun unsubscribe() {}
}

class RateListViewModelUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun viewModelSubscribesToRates() {
        //given
        val brokerMock = mockk<DataBroker>()
        every { brokerMock.subscribeToRates(any()) } just runs

        //when
        RateListViewModel(brokerMock).refresh()

        //then
        verify(exactly = 1) { brokerMock.subscribeToRates(any()) }
    }

    @Test
    fun viewModelSubscribesToRatesWithCurrencyCode() {
        //given
        val brokerMock = mockk<DataBroker>()
        every { brokerMock.subscribeToRates(any(), any()) } just runs

        //when
        RateListViewModel(brokerMock).refresh(RateItem("ZAR", 1.0))

        //then
        verify(exactly = 1) { brokerMock.subscribeToRates("ZAR", any()) }
    }

    @Test
    fun viewModelUnsubscribesOnEmpty() {
        //given
        val testBroker = spyk<DataBroker>(TestBroker(RateListResult.Empty))

        //when
        RateListViewModel(testBroker).refresh()

        //then
        verify(exactly = 1) { testBroker.subscribeToRates(any()) }
        verify(exactly = 1) { testBroker.unsubscribe() }
        confirmVerified(testBroker)
    }

    @Test
    fun viewModelUnsubscribesOnError() {
        //given
        val testBroker = spyk(TestBroker(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR)))

        //when
        RateListViewModel(testBroker).refresh()

        //then
        verify(exactly = 1) { testBroker.subscribeToRates(any()) }
        verify(exactly = 1) { testBroker.unsubscribe() }
        confirmVerified(testBroker)
    }

    @Test
    fun viewModelReSubscribesOnEmpty() {
        //given
        val testBroker = spyk<DataBroker>(TestBroker(RateListResult.Empty))

        //when
        val vm =
            RateListViewModel(testBroker)
        vm.refresh()
        vm.refresh()
        //then
        verifyOrder {
            testBroker.subscribeToRates(any())
            testBroker.unsubscribe()
            testBroker.subscribeToRates(any())
            testBroker.unsubscribe()
        }
        confirmVerified(testBroker)
    }

    @Test
    fun viewModelReSubscribesOnError() {
        //given
        val testBroker = spyk(TestBroker(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR)))

        //when
        val vm =
            RateListViewModel(testBroker)
        vm.refresh()
        vm.refresh()
        //then
        verifyOrder {
            testBroker.subscribeToRates(any())
            testBroker.unsubscribe()
            testBroker.subscribeToRates(any())
            testBroker.unsubscribe()
        }
        confirmVerified(testBroker)
    }


}
