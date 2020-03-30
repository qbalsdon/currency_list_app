package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.model.ProductionRateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductionRetrofitServiceInterface {
    @GET("api/android/latest")
    fun getRatesWith(): Call<ProductionRateResponse>

    @GET("api/android/latest")
    fun getRatesWith(@Query("base") baseCurrencyCode: String): Call<ProductionRateResponse>
}