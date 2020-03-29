package com.balsdon.ratesapp

import android.app.Application
import android.content.Context
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnitRunner
import com.balsdon.ratesapp.matchers.EspressoTestsMatchers
import com.balsdon.ratesapp.matchers.RecyclerViewMatcher
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher


class TestRunner : AndroidJUnitRunner() {

    companion object {
        enum class RateItemViewElement(val id: Int) {
            CODE(R.id.list_item_rate_currency_iso),
            NAME(R.id.list_item_rate_currency_name),
            AMOUNT(R.id.list_item_rate_currency_rate),
            IMAGE(R.id.list_item_rate_currency_flag)
        }

        private fun assertHeaderItem(element: RateItemViewElement, matcher: Matcher<View>) {
            Espresso.onView(
                CoreMatchers.allOf(
                    ViewMatchers.withId(element.id),
                    ViewMatchers.isDescendantOfA(ViewMatchers.withId(R.id.rate_list_base))
                )
            ).check(ViewAssertions.matches(matcher))
        }

        fun assertHeaderHas(code: String, name: String, value: String, drawableId: Int) {
            assertHeaderItem(
                RateItemViewElement.CODE,
                ViewMatchers.withText(code)
            )
            assertHeaderItem(
                RateItemViewElement.NAME,
                ViewMatchers.withText(name)
            )
            assertHeaderItem(
                RateItemViewElement.AMOUNT,
                ViewMatchers.withText(value)
            )
            assertHeaderItem(
                RateItemViewElement.IMAGE,
                EspressoTestsMatchers.withDrawable(drawableId)
            )
        }

        private fun assertListItem(
            elementIndex: Int,
            element: RateItemViewElement,
            matcher: Matcher<View>
        ) {

            Espresso.onView(
                CoreMatchers.allOf(
                    ViewMatchers.withId(element.id),
                    ViewMatchers.isDescendantOfA(
                        RecyclerViewMatcher(R.id.currency_list)
                            .atPosition(elementIndex)
                    )
                )
            ).check(ViewAssertions.matches(matcher))
        }

        fun assertListItemHas(withIndex: Int, code: String, name: String, value: String, drawableId: Int) {
            assertListItem(withIndex, RateItemViewElement.CODE,
                ViewMatchers.withText(code)
            )
            assertListItem(withIndex, RateItemViewElement.NAME,
                ViewMatchers.withText(name)
            )
            assertListItem(withIndex, RateItemViewElement.AMOUNT,
                ViewMatchers.withText(value)
            )
            assertListItem(withIndex, RateItemViewElement.IMAGE,
                EspressoTestsMatchers.withDrawable(drawableId)
            )
        }

        fun assertDefaultStatus() {
            assertHeaderHas("EUR", "Euro", "1.00", R.drawable.ic_european_union)

            assertListItemHas(3, "CAD", "Canadian Dollar", "6.00", R.drawable.ic_canada)
            assertListItemHas(4, "CHF", "Swiss Franc", "8.00", R.drawable.ic_switzerland)
            assertListItemHas(5, "CNY", "Chinese Yuan", "10.00", R.drawable.ic_china)
            assertListItemHas(6, "CZK", "Czech Koruna", "12.00", R.drawable.ic_czech_republic)
        }
    }

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, EspressoApplication::class.java.name, context)
    }
}