package com.balsdon.ratesapp.behaviour

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.balsdon.ratesapp.BaseRatesAppEspressoTest
import com.balsdon.ratesapp.R
import com.balsdon.ratesapp.TestRunner.Companion.assertHeaderHas
import com.balsdon.ratesapp.TestRunner.Companion.assertListItemHas
import com.balsdon.ratesapp.mocks.EspressoMockRateFetcher
import com.balsdon.ratesapp.viewAssertions.RecyclerViewHasItemCount
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RateListActivityInstrumentedTest: BaseRatesAppEspressoTest() {
    @Test
    fun displaysEuroWithRateOfOneOnStart() {
        getNextSuccessAll()
        onView(withId(R.id.rate_list_title)).check(matches(withText(R.string.app_title)))
        onView(withId(R.id.rate_list_base)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_list)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewHasCorrectItemCount() {
        getNextSuccessAll()

        onView(withId(R.id.currency_list))
            .check(RecyclerViewHasItemCount(EspressoMockRateFetcher.allCountryCodes.size - 1))
    }

    @Test
    fun changesValuesOfListWhenTextTyped() {
        getNextSuccessAll()

        onView(
            allOf(
                withId(R.id.list_item_rate_currency_rate),
                isDescendantOfA(ViewMatchers.withId(R.id.rate_list_base))
            )
        ).perform(replaceText("2.25"))

        assertHeaderHas("EUR", "Euro", "2.25",
            R.drawable.ic_european_union
        )

        assertListItemHas(3, "CAD", "Canadian Dollar", "13.50",
            R.drawable.ic_canada
        )
        assertListItemHas(4, "CHF", "Swiss Franc", "18.00",
            R.drawable.ic_switzerland
        )
    }
}

