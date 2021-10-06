package com.example.thecatapi

import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation

/*
object ActivitySwitcher {
    private const val DURATION = 300
    private const val DEPTH = 400.0f

    /* ----------------------------------------------- */
    @JvmOverloads
    fun animationIn(
        container: View,
        windowManager: WindowManager,
        listener: AnimationFinishedListener? = null
    ) {
        apply3DRotation(90f, 0f, false, container, windowManager, listener)
    }

    @JvmOverloads
    fun animationOut(
        container: View,
        windowManager: WindowManager,
        listener: AnimationFinishedListener? = null
    ) {
        apply3DRotation(0f, -90f, true, container, windowManager, listener)
    }

    /* ----------------------------------------------- */
    private fun apply3DRotation(
        fromDegree: Float,
        toDegree: Float,
        reverse: Boolean,
        container: View,
        windowManager: WindowManager,
        listener: AnimationFinishedListener?
    ) {
        val display = windowManager.defaultDisplay
        val centerX = display.width / 2.0f
        val centerY = display.height / 2.0f
        val a = Rotate3dAnimation(fromDegree, toDegree, centerX, centerY, DEPTH, reverse)
        a.reset()
        a.setDuration(DURATION)
        a.setFillAfter(true)
        a.setInterpolator(AccelerateInterpolator())
        if (listener != null) {
            a.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    listener.onAnimationFinished()
                }
            })
        }
        container.clearAnimation()
        container.startAnimation(a)
    }

    /* ----------------------------------------------- */
    interface AnimationFinishedListener {
        /**
         * Called when the animation is finished.
         */
        fun onAnimationFinished()
    }
}
*/
