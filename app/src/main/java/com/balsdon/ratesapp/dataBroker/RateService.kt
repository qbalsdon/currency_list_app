package com.balsdon.ratesapp.dataBroker

import com.balsdon.ratesapp.model.RateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RateService {
    @GET("api/android/latest")
    fun getRates(@Query("base") base: String): Call<RateResponse>
}