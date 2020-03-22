package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.service.ApiService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ScheduledDataBroker(private val service: ApiService) : DataBroker {
    companion object {
        private const val THREADS = 1
        private const val INITIAL_DELAY = 0L
        private const val INTERVAL_DELAY = 1L
        private val TIME_UNIT = TimeUnit.SECONDS
    }

    private val scheduler = Executors.newScheduledThreadPool(THREADS)

    override fun subscribeToRates(update: (RateListResult) -> Unit) {
        scheduler.scheduleAtFixedRate(
            Runnable {
                service.fetchRates(
                    update = update
                )
            },
            INITIAL_DELAY,
            INTERVAL_DELAY,
            TIME_UNIT
        )
    }

    override fun unsubscribe() {
        scheduler.shutdown()
    }
}