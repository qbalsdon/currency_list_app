package com.balsdon.ratesapp.matchers

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class DrawableMatcher internal constructor(private val expectedId: Int) :
    TypeSafeMatcher<View>(View::class.java) {

    companion object {
        const val EMPTY = 0
        const val ANY = -1
    }

    private var resourceName: String? = null

    override fun matchesSafely(target: View?): Boolean {
        if (target !is ImageView) {
            return false
        }
        val imageView: ImageView = target as ImageView
        if (expectedId == EMPTY) {
            return imageView.drawable == null
        }
        if (expectedId == ANY) {
            return imageView.drawable != null
        }
        val resources: Resources = target.getContext().getResources()
        val expectedDrawable: Drawable =
            resources.getDrawable(expectedId)
        resourceName = resources.getResourceEntryName(expectedId)
        if (expectedDrawable == null) {
            return false
        }
        val bitmap = getBitmap(imageView.getDrawable())
        val otherBitmap = getBitmap(expectedDrawable)
        return bitmap.sameAs(otherBitmap)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }
}