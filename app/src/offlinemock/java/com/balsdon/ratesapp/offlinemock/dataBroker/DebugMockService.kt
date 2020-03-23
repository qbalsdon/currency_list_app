package com.balsdon.ratesapp.offlinemock.dataBroker

import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateResponse
import com.balsdon.ratesapp.service.ApiService
import java.util.*

class DebugMockService() : ApiService {
    companion object {
        const val RANDOM_MIN = 0.0
        const val RANDOM_MAX = 10000.0

        val randomGenerator: Random by lazy {
            Random()
        }
    }

    private fun randomDouble(): Double =
        RANDOM_MIN + (RANDOM_MAX - RANDOM_MIN) * randomGenerator.nextDouble()

    private val allCountryCodes = listOf(
        "EUR",
        "AUD",
        "BGN",
        "BRL",
        "CAD",
        "CHF",
        "CNY",
        "CZK",
        "DKK",
        "EUR",
        "GBP",
        "HKD",
        "HRK",
        "HUF",
        "IDR",
        "ILS",
        "INR",
        "ISK",
        "JPY",
        "KRW",
        "MXN",
        "MYR",
        "NOK",
        "NZD",
        "PHP",
        "PLN",
        "RON",
        "RUB",
        "SEK",
        "SGD",
        "THB",
        "USD",
        "ZAR"
    )
    private val demoCountryCodes = listOf("USD", "EUR", "SEK", "CAD")

    private fun generateSuccessResult(currencyCode: String, codeList: List<String>) =
        RateListResult.Success(
            RateResponse(
                currencyCode, codeList
                    .filter { code -> code != currencyCode }
                    .map { code -> code to randomDouble() }
                    .toMap()
            )
        )

    override fun fetchRates(
        currencyCode: String,
        update: (RateListResult) -> Unit
    ) {
        update.invoke(generateSuccessResult(currencyCode, demoCountryCodes))
    }
}