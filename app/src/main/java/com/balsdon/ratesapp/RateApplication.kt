package com.balsdon.ratesapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.OfflineBroker
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.rateItem.flagResource.AndroidCountryResourceResolver

class RateApplication : Application(), Application.ActivityLifecycleCallbacks{
    override fun onCreate() {
        super.onCreate()

        //DEPENDENCY INJECTION
        //Could have used dagger but it seems a bit much for this
        RateItem.countryResourceResolver = AndroidCountryResourceResolver()

        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(OfflineBroker())
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
