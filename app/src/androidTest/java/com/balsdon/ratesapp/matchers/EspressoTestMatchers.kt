package com.balsdon.ratesapp.matchers

object EspressoTestsMatchers {
    fun withDrawable(resourceId: Int): DrawableMatcher {
        return DrawableMatcher(resourceId)
    }

    fun noDrawable(): DrawableMatcher {
        return DrawableMatcher(-1)
    }
}