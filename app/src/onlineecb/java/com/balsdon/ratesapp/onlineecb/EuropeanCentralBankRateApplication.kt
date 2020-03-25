package com.balsdon.ratesapp.onlineecb

import com.balsdon.ratesapp.RateApplication
import com.balsdon.ratesapp.onlineecb.dataBroker.EuropeanCentralBankRateService
import com.balsdon.ratesapp.onlineecb.model.EuropeanCentralBankResponse
import com.balsdon.ratesapp.service.RateServiceCommand

class EuropeanCentralBankRateApplication : RateApplication<EuropeanCentralBankRateService, EuropeanCentralBankResponse>() {
    override fun getServiceClass() = EuropeanCentralBankRateService::class.java
    override fun createRateServiceCommand(service: EuropeanCentralBankRateService) =
        RateServiceCommand(service::getRates)
}
