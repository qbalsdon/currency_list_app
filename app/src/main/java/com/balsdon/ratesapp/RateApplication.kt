package com.balsdon.ratesapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.balsdon.ratesapp.rateItem.RateItemPresenter
import com.balsdon.ratesapp.rateItem.flagResource.AndroidCountryResourceResolver
import com.balsdon.ratesapp.view.SubscribesToObservers

abstract class RateApplication : Application(), Application.ActivityLifecycleCallbacks {
    override fun onCreate() {
        super.onCreate()

        //DEPENDENCY INJECTION
        //Could have used dagger but it seems a bit much for this
        RateItemPresenter.countryResourceResolver = AndroidCountryResourceResolver()

        registerActivityLifecycleCallbacks(this)
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