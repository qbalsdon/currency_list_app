package com.balsdon.ratesapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.model.EnvironmentResponseMapper
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.flagResource.AndroidCountryResourceResolver
import com.balsdon.ratesapp.service.RateServiceCommand
import com.balsdon.ratesapp.service.RetrofitService
import com.balsdon.ratesapp.view.SubscribesToObservers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RateApplication<T, V: EnvironmentResponseMapper> : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        const val READ_TIMEOUT = 5L
        const val CONNECT_TIMEOUT = 5L
        val TIME_UNIT = TimeUnit.SECONDS
    }

    override fun onCreate() {
        super.onCreate()

        //DEPENDENCY INJECTION
        //Could have used dagger but it seems a bit much for this
        RateItem.countryResourceResolver = AndroidCountryResourceResolver()

        registerActivityLifecycleCallbacks(this)
    }

    abstract fun getServiceClass(): Class<T>
    abstract fun createRateServiceCommand(service: T): RateServiceCommand<V>

    private val rateServiceCommand by lazy {
        val service = Retrofit.Builder()
            .baseUrl(getString(R.string.rates_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                    .Builder()
                    .readTimeout(READ_TIMEOUT, TIME_UNIT)
                    .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
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

    //doing some of the annoying lifecycle stuff that's always easy to forget
    override fun onActivityPaused(activity: Activity?) {
        if (activity is SubscribesToObservers) {
            activity.unregisterObservers()
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        if (activity is SubscribesToObservers) {
            activity.registerObservers()
        }
    }

    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityDestroyed(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {}
    override fun onActivityStopped(activity: Activity?) {}
}