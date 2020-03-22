package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.model.RateItem

sealed class RateListResult {
    enum class ErrorCode {
        SERVER_ERROR,
        TIMEOUT_ERROR,
        REQUEST_ERROR,
        GENERIC_ERROR
    }

    object Empty : RateListResult()
    class Error(val errorCode: ErrorCode) : RateListResult()
    class Success(val list : List<RateItem>) : RateListResult()
}