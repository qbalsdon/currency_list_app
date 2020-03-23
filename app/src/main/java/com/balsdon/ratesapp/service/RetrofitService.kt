package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.dataBroker.RateService
import com.balsdon.ratesapp.model.RateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitService (
    private val service: RateService
) : ApiService, Callback<RateResponse> {
    private lateinit var update: (RateListResult) -> Unit

    override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
        this.update = update
        service
            .getRates(currencyCode)
            .enqueue(this)
    }

    override fun onFailure(call: Call<RateResponse>, t: Throwable) {
        update.invoke(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR))
    }

    override fun onResponse(call: Call<RateResponse>, response: Response<RateResponse>) {
        val body = response.body()
        val rateListResult = if (body == null) {
            RateListResult.Error(RateListResult.ErrorCode.SERVER_ERROR)
        } else {
            if(body.rates.isEmpty()) {
                RateListResult.Empty
            } else {
                RateListResult.Success(body)
            }
        }

        update.invoke(rateListResult)
    }
}