package com.balsdon.ratesapp.onlineecb.dataBroker

import com.balsdon.ratesapp.onlineecb.model.EuropeanCentralBankResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EuropeanCentralBankRateService {
    @GET("latest")
    fun getRates(@Query("base") base: String): Call<EuropeanCentralBankResponse>
}