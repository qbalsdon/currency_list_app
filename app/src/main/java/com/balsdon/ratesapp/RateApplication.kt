package com.balsdon.ratesapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.dataBroker.RateService
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.flagResource.AndroidCountryResourceResolver
import com.balsdon.ratesapp.service.RetrofitService
import com.balsdon.ratesapp.view.SubscribesToObservers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RateApplication : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        private const val READ_TIMEOUT = 5L
        private const val CONNECT_TIMEOUT = 5L
        private val TIME_UNIT = TimeUnit.SECONDS
    }

    override fun onCreate() {
        super.onCreate()

        //DEPENDENCY INJECTION
        //Could have used dagger but it seems a bit much for this
        RateItem.countryResourceResolver = AndroidCountryResourceResolver()

        registerActivityLifecycleCallbacks(this)
    }

    private val service: RetrofitService by lazy {
        RetrofitService(Retrofit.Builder()
            .baseUrl(getString(R.string.rates_endpoint))
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient
                .Builder()
                .readTimeout(READ_TIMEOUT, TIME_UNIT)
                .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
                .build()
            )
            .build()
            .create(RateService::class.java))
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(ScheduledDataBroker(service))
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
