package com.balsdon.ratesapp.onlineecb

import com.balsdon.ratesapp.onlineecb.dataBroker.EuropeanCentralBankRateRetroFitServiceInterface
import com.balsdon.ratesapp.onlineecb.model.EuropeanCentralBankResponse
import com.balsdon.ratesapp.service.RetrofitCallbackGenerator
import retrofit2.Call

class EuropeanCentralBankCallbackGenerator(private val europeanCentralBankRateRetroFitServiceInterface: EuropeanCentralBankRateRetroFitServiceInterface):
    RetrofitCallbackGenerator<EuropeanCentralBankResponse> {
    override fun createDefaultCall(): Call<EuropeanCentralBankResponse> =
        europeanCentralBankRateRetroFitServiceInterface.getRates()

    override fun createCallWith(baseCurrencyCode: String): Call<EuropeanCentralBankResponse> =
        europeanCentralBankRateRetroFitServiceInterface.getRatesWith(baseCurrencyCode)

}