package com.balsdon.ratesapp.onlineecb.dataBroker

import com.balsdon.ratesapp.onlineecb.model.EuropeanCentralBankResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EuropeanCentralBankRateRetroFitServiceInterface {
    @GET("latest")
    fun getRates(): Call<EuropeanCentralBankResponse>

    @GET("latest")
    fun getRatesWith(@Query("base") base: String): Call<EuropeanCentralBankResponse>
}