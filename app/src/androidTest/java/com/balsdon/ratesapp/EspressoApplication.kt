package com.balsdon.ratesapp

import android.app.Activity
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.mocks.EspressoMockDataBroker
import com.balsdon.ratesapp.mocks.EspressoMockService

class EspressoApplication : RateApplication() {

    private val espressoMockService: EspressoMockService by lazy {
        EspressoMockService()
    }

    private val espressoMockDataBroker: EspressoMockDataBroker by lazy {
        EspressoMockDataBroker(espressoMockService)
    }

    fun setNextResult(desiredResult: EspressoMockService.ResultOption) {
        espressoMockService.setNextResult(desiredResult)
    }

    fun getNextResult() {
        espressoMockDataBroker.next()
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (activity is RequiresDataBroker) {
            activity.setDataBroker(espressoMockDataBroker)
        }
    }
}