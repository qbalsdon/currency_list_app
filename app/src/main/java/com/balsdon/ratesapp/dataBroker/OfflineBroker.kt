package com.balsdon.ratesapp.dataBroker

import android.os.AsyncTask
import com.balsdon.ratesapp.model.RateItem
import java.util.*

//TODO: Move this to a build variant that is not prod
//This is a quick and dirty offline solution that is for dev purposes only - will not be included in production release
//perhaps use this in an espresso test??

class OfflineBroker : DataBroker {

    private var timer = Timer()

    inner class OfflineAsyncTask(private val update: (RateListResult) -> Unit) :
        AsyncTask<Unit, Unit, RateListResult>() {
        private fun randomDouble(): Double {
            val MIN = 0
            val MAX = 10000
            val r = Random()
            return MIN + (MAX - MIN) * r.nextDouble()
        }

        override fun onPostExecute(result: RateListResult?) {
            if (result == null) {
                update.invoke(RateListResult.Error("Null error"))
            } else {
                update.invoke(result)
            }
            super.onPostExecute(result)
        }

        private val successResult = RateListResult.Success(
            listOf(
                RateItem("AUD", 2.0),
                RateItem("BGN", 4.2),
                RateItem("BRL", randomDouble()),
                RateItem("CAD", randomDouble()),
                RateItem("CHF", randomDouble()),
                RateItem("CNY", randomDouble()),
                RateItem("CZK", randomDouble()),
                RateItem("DKK", randomDouble()),
                RateItem("EUR", randomDouble()),
                RateItem("GBP", randomDouble()),
                RateItem("HKD", randomDouble()),
                RateItem("HRK", randomDouble()),
                RateItem("HUF", randomDouble()),
                RateItem("IDR", randomDouble()),
                RateItem("ILS", randomDouble()),
                RateItem("INR", randomDouble()),
                RateItem("ISK", randomDouble()),
                RateItem("JPY", randomDouble()),
                RateItem("KRW", randomDouble()),
                RateItem("MXN", randomDouble()),
                RateItem("MYR", randomDouble()),
                RateItem("NOK", randomDouble()),
                RateItem("NZD", randomDouble()),
                RateItem("PHP", randomDouble()),
                RateItem("PLN", randomDouble()),
                RateItem("RON", randomDouble()),
                RateItem("RUB", randomDouble()),
                RateItem("SEK", randomDouble()),
                RateItem("SGD", randomDouble()),
                RateItem("THB", randomDouble()),
                RateItem("USD", randomDouble()),
                RateItem("ZAR", randomDouble())
            )
        )

        private val uiResult = RateListResult.Success(
            listOf(
                RateItem("USD", 1183.06),
                RateItem("EUR", 2.7083157236),
                RateItem("SEK", 1.8211164269),
                RateItem("CAD", 2.793121228)
            )
        )

        private val emptyResult = RateListResult.Empty
        private val errorResult = RateListResult.Error("Some user friendly error message")

        override fun doInBackground(vararg p0: Unit?): RateListResult = uiResult

    }

    override fun subscribeToRates(update: (RateListResult) -> Unit) {
        timer.cancel()
        timer = Timer()
        val timerTask = object : TimerTask() {
            override fun run() {
                android.util.Log.d("Quintin", "Quintin executing task")
                OfflineAsyncTask(update).execute()
            }
        }

        timer.schedule(timerTask, 0, 1000)
    }

    override fun unsubscribe() {
        timer.cancel()
    }
}