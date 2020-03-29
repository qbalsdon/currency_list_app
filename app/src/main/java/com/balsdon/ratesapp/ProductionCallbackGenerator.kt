package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.RevolutRateService
import com.balsdon.ratesapp.model.RevolutRateResponse
import com.balsdon.ratesapp.service.RetrofitCallbackGenerator
import retrofit2.Call

class ProductionCallbackGenerator(val revolutRateService: RevolutRateService):
    RetrofitCallbackGenerator<RevolutRateResponse> {
    override fun createDefaultCall(): Call<RevolutRateResponse> =
        revolutRateService.getRatesWith()

    override fun createCallWith(baseCurrencyCode: String): Call<RevolutRateResponse> =
        revolutRateService.getRatesWith(baseCurrencyCode)
}