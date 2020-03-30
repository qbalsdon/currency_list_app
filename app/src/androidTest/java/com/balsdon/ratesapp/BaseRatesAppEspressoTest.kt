package com.balsdon.ratesapp

import androidx.test.rule.ActivityTestRule
import com.balsdon.ratesapp.mocks.EspressoMockService
import com.balsdon.ratesapp.view.RateListActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

open class BaseRatesAppEspressoTest {
    @get:Rule
    var activityRule: ActivityTestRule<RateListActivity> =
        ActivityTestRule(RateListActivity::class.java)

    protected val espressoApplication by lazy {
        (activityRule.activity.application as EspressoApplication)
    }

    protected fun getNextSuccessAll() {
        espressoApplication.setNextResult(EspressoMockService.ResultOption.SUCCESS_ALL)
        espressoApplication.getNextResult()
    }
}