package com.thewyp.androidadvanced

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import kotlin.math.abs

private const val TAG = "MyView"

class MyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    View(context, attrs, defStyleAttr, defStyleRes) {

    var mSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var lastX: Int = 0
    private var lastY: Int = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                restoreTouchPoint(event)
                Log.d(TAG, "onTouchEvent ACTION_DOWN: lastX:$lastX, lastY:$lastY")
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = event.x.toInt() - lastX
                val offsetY = event.y.toInt() - lastY
                Log.d(TAG, "onTouchEvent ACTION_MOVE: offsetX:$offsetX, offsetY:$offsetY")

                if (abs(offsetX) > mSlop || abs(offsetY) > mSlop) {
                    layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)

//                offsetLeftAndRight(offsetX)
//                offsetTopAndBottom(offsetY)

//                val tempLayoutParams = layoutParams as LinearLayout.LayoutParams
//                tempLayoutParams.leftMargin = left + offsetX
//                tempLayoutParams.topMargin = top + offsetY
//                layoutParams = tempLayoutParams

                    restoreTouchPoint(event)
                }
            }
            else -> {
            }
        }
        return true
    }

    private fun restoreTouchPoint(event: MotionEvent) {
        lastX = event.x.toInt()
        lastY = event.y.toInt()
    }
}