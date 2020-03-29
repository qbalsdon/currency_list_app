package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.RevolutRateService
import com.balsdon.ratesapp.model.RevolutRateResponse
import com.balsdon.ratesapp.service.RateServiceCommand

class ProductionRateApplication : RetrofitRateApplication<RevolutRateService, RevolutRateResponse>() {
    override fun getServiceClass() = RevolutRateService::class.java
    override fun createRateServiceCommand(service: RevolutRateService) =
        RateServiceCommand(ProductionCallbackGenerator(service))
}
