package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.model.RevolutRateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutRateService {
    @GET("api/android/latest")
    fun getRatesWith(): Call<RevolutRateResponse>

    @GET("api/android/latest")
    fun getRatesWith(@Query("base") baseCurrencyCode: String): Call<RevolutRateResponse>
}