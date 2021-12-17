package com.alian.schetime.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import kotlin.math.*

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also { snackBar ->
        snackBar.setAction("Ok") {
            snackBar.dismiss()
        }
    }.show()
}

class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int,
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0f, min(child.height.toFloat(), child.translationY + dy))
    }
}