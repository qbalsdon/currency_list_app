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

abstract class RetrofitRateApplication<T, V: EnvironmentResponseMapper>: RateApplication<T>() {
    abstract fun getServiceClass(): Class<T>
    abstract fun createRateServiceCommand(service: T): RateServiceCommand<V>

    private val rateServiceCommand by lazy {
        val service = Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                    .Builder()
                    .readTimeout(BuildConfig.READ_TIMEOUT_SECONDS, TIME_UNIT)
                    .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TIME_UNIT)
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
            activity.setDataBroker(ScheduledDataBroker(service, BuildConfig.REFRESH_RATE_SECONDS))
        }
    }
}