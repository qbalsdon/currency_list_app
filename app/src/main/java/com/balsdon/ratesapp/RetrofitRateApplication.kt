package com.balsdon.ratesapp

import android.app.Activity
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import com.balsdon.ratesapp.service.RateServiceCommand
import com.balsdon.ratesapp.service.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitRateApplication<T, V: EnvironmentResponseMapper>: RateApplication<T, V>() {
    abstract fun getServiceClass(): Class<T>
    abstract fun createRateServiceCommand(service: T): RateServiceCommand<V>

    private val rateServiceCommand by lazy {
        val service = Retrofit.Builder()
            .baseUrl(getString(R.string.rates_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                    .Builder()
                    .readTimeout(RateApplication.READ_TIMEOUT, RateApplication.TIME_UNIT)
                    .connectTimeout(RateApplication.CONNECT_TIMEOUT, RateApplication.TIME_UNIT)
                    .build()
            )
            .build()
            .create(getServiceClass())

        createRateServiceCommand(service)
    }

    private val service: RetrofitService<V> by lazy {
        RetrofitService(rateServiceCommand)
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(ScheduledDataBroker(service, resources.getInteger(R.integer.refresh_rate)))
        }
    }
}