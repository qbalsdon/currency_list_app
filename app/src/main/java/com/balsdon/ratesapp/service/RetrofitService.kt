package com.balsdon.ratesapp.service

import com.balsdon.ratesapp.dataBroker.RateListResult
import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitService<T : EnvironmentResponseMapper>(
    private val service: RateServiceCommand<T>
) : ApiService, Callback<T> {
    private lateinit var update: (RateListResult) -> Unit

    override fun fetchRates(update: (RateListResult) -> Unit) {
        this.update = update
        service
            .getRateCall()
            .enqueue(this)
    }

    override fun fetchRates(currencyCode: String, update: (RateListResult) -> Unit) {
        this.update = update
        service
            .getRateCall(currencyCode)
            .enqueue(this)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        update.invoke(RateListResult.Error(RateListResult.ErrorCode.GENERIC_ERROR))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val body = response.body()
        val rateListResult = if (body == null) {
            RateListResult.Error(RateListResult.ErrorCode.SERVER_ERROR)
        } else {
            if (body.isEmpty()) {
                RateListResult.Empty
            } else {
                RateListResult.Success(body.toRateResponse())
            }
        }

        update.invoke(rateListResult)
    }
}