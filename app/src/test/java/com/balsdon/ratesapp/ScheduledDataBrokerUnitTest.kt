package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.service.ApiService
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ScheduledDataBrokerUnitTest {
    @Test
    fun scheduledDataBrokerRunsEverySecond() {
        //given
        val SECONDS = 5

        val service = mockk<ApiService>()
        val broker = ScheduledDataBroker(service)

        var count = 0
        //when
        broker.subscribeToRates { _ ->
            count++
        }

        Thread.sleep(SECONDS * 1000L)

        broker.unsubscribe()
        //then
        assertEquals(5, SECONDS)
    }
}
