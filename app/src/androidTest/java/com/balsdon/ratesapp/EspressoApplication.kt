package com.balsdon.ratesapp

import android.app.Activity
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker

class EspressoApplication : RateApplication<EspressoStubService>() {
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(ScheduledDataBroker(EspressoStubService(), 1))
        }
    }
}