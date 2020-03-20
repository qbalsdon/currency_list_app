package com.balsdon.ratesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.balsdon.ratesapp.dataBroker.DataBroker
import com.balsdon.ratesapp.dataBroker.RateListResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RateListViewModelUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val brokerMock = mockk<DataBroker>()
    private val viewModel = RateListViewModel(brokerMock)

    @Test
    fun viewModelCanGetEmptyListOfRates() {
        //given
        every { brokerMock.getRates() } returns RateListResult.Empty

        //when
        viewModel.getRateList()
        viewModel.getRateList()

        //then
        verify (exactly = 1) { brokerMock.getRates() }
    }

    @Test
    fun viewModelCanHandleError() {
        //given
        every { brokerMock.getRates() } returns RateListResult.Error("")

        //when
        viewModel.getRateList()
        viewModel.getRateList()

        //then
        verify (exactly = 1) { brokerMock.getRates() }
    }

    @Test
    fun viewModelCanHandleSuccessRateList() {
        //given
        every { brokerMock.getRates() } returns RateListResult.Success(mockk())

        //when
        viewModel.getRateList()
        viewModel.getRateList()

        //then
        verify (exactly = 1) { brokerMock.getRates() }
    }
}
