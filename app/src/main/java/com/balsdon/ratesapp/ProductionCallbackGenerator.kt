package com.balsdon.ratesapp

import com.balsdon.ratesapp.dataBroker.ProductionRetrofitServiceInterface
import com.balsdon.ratesapp.model.ProductionRateResponse
import com.balsdon.ratesapp.service.RetrofitCallbackGenerator
import retrofit2.Call

class ProductionCallbackGenerator(val productionRetrofitServiceInterface: ProductionRetrofitServiceInterface):
    RetrofitCallbackGenerator<ProductionRateResponse> {
    override fun createDefaultCall(): Call<ProductionRateResponse> =
        productionRetrofitServiceInterface.getRatesWith()

    override fun createCallWith(baseCurrencyCode: String): Call<ProductionRateResponse> =
        productionRetrofitServiceInterface.getRatesWith(baseCurrencyCode)
}