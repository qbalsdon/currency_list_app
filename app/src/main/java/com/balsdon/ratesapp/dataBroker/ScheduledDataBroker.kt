package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.service.ApiService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class ScheduledDataBroker(private val service: ApiService, private val intervalDelaySeconds: Int) : DataBroker {
    companion object {
        private const val THREADS = 1
        private const val INITIAL_DELAY = 0L
        private val TIME_UNIT = TimeUnit.SECONDS
    }

    private val scheduler = Executors.newScheduledThreadPool(THREADS)
    private var hasError: AtomicBoolean = AtomicBoolean(false)
    private var currentFuture: ScheduledFuture<*>? = null

    private fun updateSafely(result: RateListResult, update: (RateListResult) -> Unit) {
        if(!hasError.get()) {
            update.invoke(result)
            hasError = AtomicBoolean((result !is RateListResult.Success))
        }
    }

    override fun subscribeToRates(update: (RateListResult) -> Unit) {
        hasError = AtomicBoolean(false)
        currentFuture = scheduler.scheduleWithFixedDelay(
            Runnable {
                service.fetchRates(
                    update = {
                        updateSafely(it, update)
                    }
                )
            },
            INITIAL_DELAY,
            intervalDelaySeconds.toLong(),
            TIME_UNIT
        )
    }

    override fun subscribeToRates(currencyCode: String, update: (RateListResult) -> Unit) {
        hasError = AtomicBoolean(false)
        currentFuture = scheduler.scheduleWithFixedDelay(
            Runnable {
                service.fetchRates(
                    currencyCode = currencyCode,
                    update = {
                        updateSafely(it, update)
                    }
                )
            },
            INITIAL_DELAY,
            intervalDelaySeconds.toLong(),
            TIME_UNIT
        )
    }

    override fun unsubscribe() {
        currentFuture?.cancel(true)
    }
}