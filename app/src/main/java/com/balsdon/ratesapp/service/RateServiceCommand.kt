package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import retrofit2.Call

class RateServiceCommand<T : EnvironmentResponseMapper>(private val rateCall: ((String)-> Call<T>)) {
    fun getRateCall(base: String) : Call<T> = rateCall.invoke(base)
}