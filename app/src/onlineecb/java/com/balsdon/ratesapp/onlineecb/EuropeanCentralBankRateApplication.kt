package com.balsdon.ratesapp.onlineecb

import com.balsdon.ratesapp.RetrofitRateApplication
import com.balsdon.ratesapp.onlineecb.dataBroker.EuropeanCentralBankRateRetroFitServiceInterface
import com.balsdon.ratesapp.onlineecb.model.EuropeanCentralBankResponse
import com.balsdon.ratesapp.service.RateServiceCommand

class EuropeanCentralBankRateApplication : RetrofitRateApplication<EuropeanCentralBankRateRetroFitServiceInterface, EuropeanCentralBankResponse>() {
    override fun getServiceClass() = EuropeanCentralBankRateRetroFitServiceInterface::class.java
    override fun createRateServiceCommand(service: EuropeanCentralBankRateRetroFitServiceInterface) =
        RateServiceCommand(EuropeanCentralBankCallbackGenerator(service))
}
