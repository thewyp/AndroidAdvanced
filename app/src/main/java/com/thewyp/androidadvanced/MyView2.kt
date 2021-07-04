package com.thewyp.androidadvanced

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import kotlin.math.abs

private const val TAG = "MyView2"

class MyView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var mScroller: Scroller = Scroller(context)

    private var mVelocityTracker: VelocityTracker? = VelocityTracker.obtain()

    var mPaint: Paint = Paint()
    var mSlop = ViewConfiguration.get(context).scaledTouchSlop

    var lastX = 0
    var lastY = 0

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.RED
        isClickable = true


    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll: ")
            scrollTo(mScroller.currX, mScroller.currY)
            if (scrollX == mScroller.currX && scrollY == mScroller.currY) {
                postInvalidate()
            }
        } else {
            Log.d(TAG, "computeScroll is over: ")
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.GRAY)

        canvas.save()
        canvas.translate(100f, 100f)
        canvas.drawCircle(0f, 0f, 40.0f, mPaint)
        canvas.drawCircle(50f, 50f, 40.0f, mPaint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mVelocityTracker?.addMovement(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                restoreTouchPoint(event)
            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = event.x.toInt() - lastX
                val offsetY = event.y.toInt() - lastY
                if (abs(offsetX) > mSlop || abs(offsetY) > mSlop) {
                    scrollBy(-offsetX, -offsetY)
                    restoreTouchPoint(event)
                }
            }
            MotionEvent.ACTION_UP -> {
                mVelocityTracker?.computeCurrentVelocity(1000, 2000.0f)
                var velocityX = mVelocityTracker?.xVelocity?.toInt() ?: 0
                var velocityY = mVelocityTracker?.yVelocity?.toInt() ?: 0
                if (abs(velocityX) > 0 || abs(velocityY) > 0) {
//                    velocityX = if (abs(velocityX) > abs(velocityX)) velocityX else 0
//                    velocityY = if (velocityX == 0) velocityY else 0
                    mScroller.fling(
                        scrollX,
                        scrollY,
                        -velocityX,
                        -velocityY,
                        -1000,
                        1000,
                        -1000,
                        2000
                    )
                    invalidate()
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

    fun startScroll(dx: Int, dy: Int) {
        val holderX: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("scrollX", scrollX, scrollX + dx)
        val holderY: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("scrollY", scrollY, scrollY + dy)
        val animator = ObjectAnimator.ofPropertyValuesHolder(this, holderX, holderY)
        animator.duration = 1000
        animator.addUpdateListener {
            invalidate()
        }
        animator.start()
    }
}