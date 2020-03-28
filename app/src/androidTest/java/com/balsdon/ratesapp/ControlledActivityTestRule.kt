package com.balsdon.ratesapp

import android.app.Activity
import androidx.test.rule.ActivityTestRule

class ControlledActivityTestRule<T : Activity?> : ActivityTestRule<T> {
    constructor(activityClass: Class<T>?) : super(activityClass, false)
    constructor(activityClass: Class<T>?, initialTouchMode: Boolean) : super(
        activityClass,
        initialTouchMode,
        true
    )

    constructor(
        activityClass: Class<T>?,
        initialTouchMode: Boolean,
        launchActivity: Boolean
    ) : super(activityClass, initialTouchMode, launchActivity)

    fun finish() {
        finishActivity()
    }

    fun relaunchActivity() {
        finishActivity()
        launchActivity()
    }

    private fun launchActivity() {
        launchActivity(activityIntent)
    }
}