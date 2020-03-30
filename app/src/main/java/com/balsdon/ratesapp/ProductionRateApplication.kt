package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.ProductionRetrofitServiceInterface
import com.balsdon.ratesapp.model.ProductionRateResponse
import com.balsdon.ratesapp.service.RateServiceCommand

class ProductionRateApplication : RetrofitRateApplication<ProductionRetrofitServiceInterface, ProductionRateResponse>() {
    override fun getServiceClass() = ProductionRetrofitServiceInterface::class.java
    override fun createRateServiceCommand(service: ProductionRetrofitServiceInterface) =
        RateServiceCommand(ProductionCallbackGenerator(service))
}
