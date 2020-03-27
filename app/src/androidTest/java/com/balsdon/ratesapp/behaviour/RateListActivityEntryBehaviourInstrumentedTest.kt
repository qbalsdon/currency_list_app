package com.balsdon.ratesapp.behaviour

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.balsdon.ratesapp.EspressoApplication
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.TestRunner.Companion.assertHeaderHas
import com.balsdon.ratesapp.TestRunner.Companion.assertListItemHas
import com.balsdon.ratesapp.view.RateListActivity
import com.balsdon.ratesapp.view.RateListAdapter
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RateListActivityEntryBehaviourInstrumentedTest {
    @get:Rule
    var activityRule: ActivityTestRule<RateListActivity> =
        ActivityTestRule(RateListActivity::class.java)

    //Assert we're testing with the right application
    @Test
    @Throws(Exception::class)
    fun testRunningTheCorrectApplication() {
        assertEquals(
            EspressoApplication::class.java.simpleName,
            activityRule.activity.application.javaClass.simpleName
        )
    }

    @Test
    fun recyclerViewClickOnItemChangesMain() {
        onView(withId(R.id.currency_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RateListAdapter.RateItemViewHolder>(
                    3,
                    click()
                )
            )

        assertHeaderHas("CAD", "Canadian Dollar", "6.00", R.drawable.ic_canada)
        assertListItemHas(4, "CHF", "Swiss Franc", "48.00", R.drawable.ic_switzerland)
        assertListItemHas(5, "CNY", "Chinese Yuan", "60.00", R.drawable.ic_china)
        assertListItemHas(6, "CZK", "Czech Koruna", "72.00", R.drawable.ic_czech_republic)
    }
}

