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
    val service: RateService
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
        update.invoke(
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val list = response.body()!!.toRateItemList()
                    if (list.isNotEmpty())
                        RateListResult.Success(list)
                    else
                        RateListResult.Empty
                } else {
                    RateListResult.Error(RateListResult.ErrorCode.SERVER_ERROR)
                }
            } else RateListResult.Error(RateListResult.ErrorCode.REQUEST_ERROR)
        )
    }
}