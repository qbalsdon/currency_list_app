package com.balsdon.ratesapp.offlinemock.dataBroker

import com.balsdon.ratesapp.dataBroker.RateFetcher
import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.model.RateResponse
import java.util.*

class DebugMockRateFetcher : RateFetcher {
    companion object {
        const val RANDOM_MIN = 0.0
        const val RANDOM_MAX = 999.0

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
    private var stepCounter = 0

    private fun generateSuccessResult(currencyCode: String, codeList: List<String>) =
        RateListResult.Success(
            RateResponse(
                currencyCode, codeList
                    .filter { code -> code != currencyCode }
                    .map { code -> RateItem(code , randomDouble()) }
                    .toList()
            )
        )

    private fun generateErrorResult() =
        RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR)

    override fun fetchRates(
        update: (RateListResult) -> Unit
    ) {
       fetchRates("EUR", update)
    }

    override fun fetchRates(
        currencyCode: String,
        update: (RateListResult) -> Unit
    ) {
        //Thread.sleep(5000L)

        when (stepCounter) {
            0,// -> update.invoke(generateErrorResult())
            1,// -> update.invoke(generateErrorResult())
            2 -> update.invoke(generateSuccessResult(currencyCode, allCountryCodes))
        }
        stepCounter = (stepCounter + 1) % 3
    }
}