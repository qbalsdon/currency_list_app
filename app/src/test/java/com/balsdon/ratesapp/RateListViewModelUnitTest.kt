package com.balsdon.ratesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.view.RateListViewModel
import io.mockk.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RateListViewModelUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun viewModelSubscribesToRates() {
        //given
        val brokerMock = mockk<DataBroker>()
        every { brokerMock.subscribeToRates(any()) } just runs

        //when
        RateListViewModel(brokerMock)

        //then
        verify(exactly = 1) { brokerMock.subscribeToRates(any()) }
    }

    @Test
    fun viewModelUnsubscribesOnEmpty() {
        //given
        val testBroker = spyk(object : DataBroker {
            override fun subscribeToRates(update: (RateListResult) -> Unit) {
                update.invoke(RateListResult.Empty)
            }

            override fun unsubscribe() {}
        })

        //when
        RateListViewModel(testBroker)

        //then
        verify(exactly = 1) { testBroker.subscribeToRates(any()) }
        verify(exactly = 1) { testBroker.unsubscribe() }
        confirmVerified(testBroker)
    }

    @Test
    fun viewModelUnsubscribesOnError() {
        //given
        val testBroker = spyk(object : DataBroker {
            override fun subscribeToRates(update: (RateListResult) -> Unit) {
                update.invoke(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR))
            }

            override fun unsubscribe() {}
        })

        //when
        RateListViewModel(testBroker)

        //then
        verify(exactly = 1) { testBroker.subscribeToRates(any()) }
        verify(exactly = 1) { testBroker.unsubscribe() }
        confirmVerified(testBroker)
    }

    @Test
    fun viewModelReSubscribesOnEmpty() {
        //given
        val testBroker = spyk(object : DataBroker {
            override fun subscribeToRates(update: (RateListResult) -> Unit) {
                update.invoke(RateListResult.Empty)
            }

            override fun unsubscribe() {}
        })

        //when
        val vm = RateListViewModel(testBroker)
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
        val testBroker = spyk(object : DataBroker {
            override fun subscribeToRates(update: (RateListResult) -> Unit) {
                update.invoke(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR))
            }

            override fun unsubscribe() {}
        })

        //when
        val vm = RateListViewModel(testBroker)
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
