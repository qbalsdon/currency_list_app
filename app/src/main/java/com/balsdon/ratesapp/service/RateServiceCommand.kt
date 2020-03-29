package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import retrofit2.Call

class RateServiceCommand<T : EnvironmentResponseMapper>(private val retrofitCallbackGenerator: RetrofitCallbackGenerator<T>) {
    fun getRateCall() : Call<T> = retrofitCallbackGenerator.createDefaultCall()
    fun getRateCall(baseCurrencyCode: String) : Call<T> = retrofitCallbackGenerator.createCallWith(baseCurrencyCode)
}