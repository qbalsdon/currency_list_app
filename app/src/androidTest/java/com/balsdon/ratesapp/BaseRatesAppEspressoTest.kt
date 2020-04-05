package com.balsdon.ratesapp

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.balsdon.ratesapp.mocks.EspressoMockRateFetcher
import com.balsdon.ratesapp.view.RateListActivity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class BaseRatesAppEspressoTest {
    @get:Rule
    var activityRule: ActivityTestRule<RateListActivity> =
        ActivityTestRule(RateListActivity::class.java,true, false)

    protected val espressoApplication by lazy {
        (activityRule.activity.application as EspressoApplication)
    }

    @Before
    fun clearPreferences() {
        val targetContext: Context = getInstrumentation().targetContext
        PreferenceManager.getDefaultSharedPreferences(targetContext).edit().apply {
            clear()
            apply()
        }

        activityRule.launchActivity(Intent())
    }

    @Test
    @Throws(Exception::class)
    fun testRunningTheCorrectApplication() {
        Assert.assertEquals(
            EspressoApplication::class.java.simpleName,
            activityRule.activity.application.javaClass.simpleName
        )
    }

    protected fun getNextSuccessAll() {
        espressoApplication.setNextResult(EspressoMockRateFetcher.ResultOption.SUCCESS_ALL)
        espressoApplication.getNextResult()
    }
}