package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import retrofit2.Call

interface RetrofitCallbackGenerator<T: EnvironmentResponseMapper> {
    fun createDefaultCall() : Call<T>
    fun createCallWith(baseCurrencyCode: String) : Call<T>
}