package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class ScheduledDataBrokerUnitTest {
    companion object{
        private const val ONE_SECOND = 1000L
    }

    @Test
    fun scheduledDataBrokerRunsEverySecond() {
        //given
        val seconds = 5

        val service = object:ApiService{
            override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
                update.invoke(RateListResult.Success(mockk()))
            }
        }
        val broker = ScheduledDataBroker(service)

        var count = 0
        //when
        broker.subscribeToRates { _ ->
            count += 1
        }

        Thread.sleep(seconds * ONE_SECOND)

        broker.unsubscribe()
        //then
        assertEquals(seconds, count)
    }

    @Test
    fun scheduledDataBrokerRunsEverySecondSequentially() {
        //given
        val seconds = 3

        val service = spyk(object:ApiService{
            override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
                Thread.sleep(ONE_SECOND)
                update.invoke(RateListResult.Success(mockk()))
            }
        })
        val broker = ScheduledDataBroker(service)

        var count = 0
        //when

        broker.subscribeToRates { _ ->
            count++
        }

        Thread.sleep(seconds * 2 * ONE_SECOND)

        broker.unsubscribe()
        //then
        assertEquals(seconds, count)
    }

    @Test
    fun scheduledDataBrokerNotifiesOnlyOneError() {
        //given
        val service = spyk(object : ApiService {
            override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
                update.invoke(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR))
                update.invoke(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR))
            }
        })
        val broker = spyk(ScheduledDataBroker(service))
        var count = 0
        //when
        broker.subscribeToRates { _ -> count += 1 }
        Thread.sleep(ONE_SECOND)
        //then
        assertEquals(1, count)
    }
}
