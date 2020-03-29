package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.service.ApiService
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class TestApiService(private val result: RateListResult = RateListResult.Success(mockk()), private val delay: Long = 0L,private val repeat: Int = 1): ApiService {
    override fun fetchRates(update: (RateListResult) -> Unit) {
        if (delay > 0) {
            Thread.sleep(delay)
        }
        for (i in 1..repeat)
            update.invoke(result)
    }

    override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
        fetchRates(update)
    }
}

class ScheduledDataBrokerUnitTest {
    companion object{
        private const val DELAY = 1
        private const val ONE_SECOND = 1000L
    }

    @Test
    fun scheduledDataBrokerRunsEverySecond() {
        //given
        val seconds = 5

        val service = TestApiService()
        val broker = ScheduledDataBroker(service, DELAY)

        var count = 0
        //when
        broker.subscribeToRates("") { _ ->
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

        val service = spyk(TestApiService(delay = ONE_SECOND))
        val broker = ScheduledDataBroker(service, DELAY)

        var count = 0
        //when

        broker.subscribeToRates("") { _ ->
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
        val service = spyk(TestApiService(
            result = RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR),
            repeat = 2
        ))
        val broker = spyk(ScheduledDataBroker(service, DELAY))
        var count = 0
        //when
        broker.subscribeToRates("") { _ -> count += 1 }
        Thread.sleep(ONE_SECOND)
        //then
        assertEquals(1, count)
    }

    @Test
    fun canUnSubscribeWithoutSubscribing() {
        //given
        val broker = spyk(ScheduledDataBroker(mockk(relaxed = true), DELAY))

        //when
        broker.unsubscribe()

        verify (exactly = 0) { broker.subscribeToRates(any(), any()) }
    }

    @Test
    fun canSubscribeAfterUnSubscribing() {
        //given
        val broker = spyk(ScheduledDataBroker(mockk(relaxed = true), DELAY))

        //when
        broker.subscribeToRates("") { _ -> }
        broker.unsubscribe()
        broker.subscribeToRates("") { _ -> }

        verify (exactly = 2) { broker.subscribeToRates(any(), any()) }
    }
}
