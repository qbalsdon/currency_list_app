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

    //Assert we're testing with the right application
    @Test
    @Throws(Exception::class)
    fun testRunningTheCorrectApplication() {
        Assert.assertEquals(
            EspressoApplication::class.java.simpleName,
            activityRule.activity.application.javaClass.simpleName
        )
    }

    protected fun getNextSuccessAll() {
        espressoApplication.setNextResult(EspressoMockService.ResultOption.SUCCESS_ALL)
        espressoApplication.getNextResult()
    }
}