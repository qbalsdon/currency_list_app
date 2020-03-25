package com.balsdon.ratesapp.onlineecb.model

import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.model.RateResponse

data class EuropeanCentralBankResponse(val base: String, val rates: Map<String, Double>) :
    EnvironmentResponseMapper {

    override fun toRateResponse() =
        RateResponse(base, rates.map { RateItem(it.key, it.value) })

    override fun isEmpty() = rates.isEmpty()
}