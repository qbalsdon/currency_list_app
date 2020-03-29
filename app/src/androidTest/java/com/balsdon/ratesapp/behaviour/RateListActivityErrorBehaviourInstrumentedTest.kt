package com.balsdon.ratesapp.behaviour

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.balsdon.ratesapp.BaseRatesAppEspressoTest
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.TestRunner.Companion.assertDefaultStatus
import com.balsdon.ratesapp.mocks.EspressoMockService
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RateListActivityErrorBehaviourInstrumentedTest : BaseRatesAppEspressoTest() {
    @Test
    fun recyclerViewErrorDisplaysSnackbar() {
        assertDefaultStatus()

        espressoApplication.setNextResult(EspressoMockService.ResultOption.ERROR)
        espressoApplication.getNextResult()

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.error_generic)))
    }
}

