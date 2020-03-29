package com.balsdon.ratesapp.mocks

import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.model.RateResponse
import com.balsdon.ratesapp.service.ApiService

class EspressoMockService : ApiService {
    companion object {
        val allCountryCodes = listOf(
            "EUR",
            "AUD",
            "BGN",
            "BRL",
            "CAD",
            "CHF",
            "CNY",
            "CZK",
            "DKK",
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

        val demoCountryCodes = listOf("USD", "EUR", "SEK", "CAD")
    }

    enum class ResultOption {
        ERROR, EMPTY, SUCCESS_ALL, SUCCESS_DEMO
    }

    private var nextResultState =
        ResultOption.SUCCESS_ALL //default state

    private fun generateSuccessResult(currencyCode: String, codeList: List<String>) =
        RateListResult.Success(
            RateResponse(
                currencyCode, codeList
                    .filter { code -> code != currencyCode }
                    .mapIndexed { index, code -> RateItem(code , index * 2.0) }
                    .toList()
            )
        )

    private fun generateErrorResult() =
        RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR)

    private fun generateEmptyResult() =
        RateListResult.Empty

    override fun fetchRates(
        currencyCode: String,
        update: (RateListResult) -> Unit
    ) {
        update.invoke(
        when (nextResultState) {
            ResultOption.ERROR -> generateErrorResult()
            ResultOption.EMPTY -> generateEmptyResult()
            ResultOption.SUCCESS_DEMO -> generateSuccessResult(currencyCode,
                demoCountryCodes
            )
            ResultOption.SUCCESS_ALL -> generateSuccessResult(currencyCode,
                allCountryCodes
            )
        })
    }

    fun setNextResult(desiredResult: ResultOption) {
        nextResultState = desiredResult
    }
}