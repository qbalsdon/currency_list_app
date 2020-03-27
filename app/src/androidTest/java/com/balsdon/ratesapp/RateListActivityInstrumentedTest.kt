package com.balsdon.ratesapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.balsdon.ratesapp.TestRunner.Companion.assertHeaderHas
import com.balsdon.ratesapp.TestRunner.Companion.assertListItemHas
import com.balsdon.ratesapp.view.RateListActivity
import com.balsdon.ratesapp.viewAssertions.RecyclerViewHasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RateListActivityInstrumentedTest {

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

    private fun assertDefaultStatus() {
        assertHeaderHas("EUR", "Euro", "1.00", R.drawable.ic_european_union)

        assertListItemHas(3, "CAD", "Canadian Dollar", "6.00", R.drawable.ic_canada)
        assertListItemHas(4, "CHF", "Swiss Franc", "8.00", R.drawable.ic_switzerland)
        assertListItemHas(5, "CNY", "Chinese Yuan", "10.00", R.drawable.ic_china)
        assertListItemHas(6, "CZK", "Czech Koruna", "12.00", R.drawable.ic_czech_republic)
    }

    @Test
    fun displaysEuroWithRateOfOneOnStart() {
        onView(withId(R.id.rate_list_title)).check(matches(withText("Rates")))
        onView(withId(R.id.rate_list_base)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_list)).check(matches(isDisplayed()))

        assertDefaultStatus()
    }

    @Test
    fun recyclerViewHasCorrectItemCount() {
        onView(withId(R.id.currency_list))
            .check(RecyclerViewHasItemCount(EspressoStubService.allCountryCodes.size - 1))
    }

    @Test
    fun changesValuesOfListWhenTextTyped() {
        assertDefaultStatus()

        onView(
            allOf(
                withId(R.id.list_item_rate_currency_rate),
                isDescendantOfA(ViewMatchers.withId(R.id.rate_list_base))
            )
        ).perform(replaceText("2.25"))

        assertHeaderHas("EUR", "Euro", "2.25", R.drawable.ic_european_union)

        assertListItemHas(3, "CAD", "Canadian Dollar", "13.50", R.drawable.ic_canada)
        assertListItemHas(4, "CHF", "Swiss Franc", "18.00", R.drawable.ic_switzerland)
        assertListItemHas(5, "CNY", "Chinese Yuan", "22.50", R.drawable.ic_china)
        assertListItemHas(6, "CZK", "Czech Koruna", "27.00", R.drawable.ic_czech_republic)

        onView(
            allOf(
                withId(R.id.list_item_rate_currency_rate),
                isDescendantOfA(ViewMatchers.withId(R.id.rate_list_base))
            )
        ).perform(replaceText("1.00"))

        assertDefaultStatus()
    }
}

