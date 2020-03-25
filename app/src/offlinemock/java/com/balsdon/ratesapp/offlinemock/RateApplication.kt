package com.balsdon.ratesapp.offlinemock

import android.app.Activity
import android.os.Bundle
import com.balsdon.ratesapp.RateApplication
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.dataBroker.ScheduledDataBroker
import com.balsdon.ratesapp.offlinemock.dataBroker.DebugMockService
import com.balsdon.ratesapp.service.RateServiceCommand

class RateApplication : RateApplication<DebugMockService>() {
    override fun getServiceClass(): Class<DebugMockService> = DebugMockService::class.java
    override fun createRateServiceCommand(service: DebugMockService): RateServiceCommand = RateServiceCommand { _ -> throw NotImplementedError("This is not used in the offline mock") }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(ScheduledDataBroker(DebugMockService()))
        }
    }
}
