package com.balsdon.ratesapp.offlinemock

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.balsdon.ratesapp.RateApplication
import com.balsdon.ratesapp.SubscribesToObservers
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.model.RateItem
import com.balsdon.ratesapp.offlinemock.dataBroker.DebugMockService
import com.balsdon.ratesapp.rateItem.flagResource.AndroidCountryResourceResolver

class RateApplication : RateApplication() {
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(ScheduledDataBroker(DebugMockService()))
        }
    }
}
