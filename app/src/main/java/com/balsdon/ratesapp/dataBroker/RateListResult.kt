package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.model.RateItem

sealed class RateListResult {
    object Empty : RateListResult()
    class Error (val message: String) : RateListResult()
    class Success(val list : List<RateItem>) : RateListResult()
}