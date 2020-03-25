package com.balsdon.ratesapp

import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.model.RateResponse

class TestData {
    companion object {
        val testRateResponse =
            RateResponse(
                "AUD",
                listOf(
                    RateItem("BGN", 1.26),
                    RateItem("BRL", 2.649),
                    RateItem("CAD", 0.956),
                    RateItem("CHF", 0.725),
                    RateItem("CNY", 4.891),
                    RateItem("CZK", 16.248),
                    RateItem("DKK", 4.804),
                    RateItem("EUR", 0.636),
                    RateItem("GBP", 0.56),
                    RateItem("HKD", 5.647),
                    RateItem("HRK", 4.761),
                    RateItem("HUF", 201.432),
                    RateItem("IDR", 10112.164),
                    RateItem("ILS", 2.624),
                    RateItem("INR", 51.717),
                    RateItem("ISK", 86.098),
                    RateItem("JPY", 79.946),
                    RateItem("KRW", 818.313),
                    RateItem("MXN", 13.959),
                    RateItem("MYR", 2.955),
                    RateItem("NOK", 6.181),
                    RateItem("NZD", 1.058),
                    RateItem("PHP", 37.524),
                    RateItem("PLN", 2.764),
                    RateItem("RON", 3.023),
                    RateItem("RUB", 48.085),
                    RateItem("SEK", 6.755),
                    RateItem("SGD", 0.983),
                    RateItem("THB", 22.363),
                    RateItem("USD", 0.727),
                    RateItem("ZAR", 10.092)
                )
            )
    }
}