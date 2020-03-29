package com.balsdon.ratesapp.onlineecb

import com.balsdon.ratesapp.onlineecb.dataBroker.EuropeanCentralBankRateService
import com.balsdon.ratesapp.onlineecb.model.EuropeanCentralBankResponse
import com.balsdon.ratesapp.service.RetrofitCallbackGenerator
import retrofit2.Call

class EuropeanCentralBankCallbackGenerator(private val europeanCentralBankRateService: EuropeanCentralBankRateService):
    RetrofitCallbackGenerator<EuropeanCentralBankResponse> {
    override fun createDefaultCall(): Call<EuropeanCentralBankResponse> =
        europeanCentralBankRateService.getRates()

    override fun createCallWith(baseCurrencyCode: String): Call<EuropeanCentralBankResponse> =
        europeanCentralBankRateService.getRatesWith(baseCurrencyCode)

}