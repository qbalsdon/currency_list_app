package com.balsdon.ratesapp

import android.app.Activity
import android.os.Bundle
import com.balsdon.ratesapp.dataBroker.RequiresDataBroker
import com.balsdon.ratesapp.mocks.EspressoMockDataBroker
import com.balsdon.ratesapp.mocks.EspressoMockRateFetcher

class EspressoApplication : RateApplication() {

    private val espressoMockRateFetcher: EspressoMockRateFetcher by lazy {
        EspressoMockRateFetcher()
    }

    private val espressoMockDataBroker: EspressoMockDataBroker by lazy {
        EspressoMockDataBroker(espressoMockRateFetcher)
    }

    fun setNextResult(desiredResult: EspressoMockRateFetcher.ResultOption) {
        espressoMockRateFetcher.setNextResult(desiredResult)
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