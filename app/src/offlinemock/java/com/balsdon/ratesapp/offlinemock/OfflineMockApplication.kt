package com.balsdon.ratesapp.offlinemock

import android.app.Activity
import android.os.Bundle
import com.balsdon.ratesapp.RateApplication
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.offlinemock.dataBroker.DebugMockRateFetcher

class OfflineMockApplication : RateApplication() {
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(ScheduledDataBroker(DebugMockRateFetcher(), 1))
        }
    }
}
