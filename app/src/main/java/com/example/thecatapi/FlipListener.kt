package com.example.thecatapi

import android.animation.ValueAnimator
import android.view.View


/*
fun FlipListener(front: View, back: View) {
    this.mFrontView = front
    this.mBackView = back
    this.mBackView.setVisibility(View.GONE)
}

fun onAnimationUpdate(animation: ValueAnimator) {
    val value = animation.animatedFraction
    val scaleValue = 0.625f + 1.5f * (value - 0.5f) * (value - 0.5f)
    if (value <= 0.5f) {
        this.mFrontView.setRotationY(180 * value)
        this.mFrontView.setScaleX(scaleValue)
        this.mFrontView.setScaleY(scaleValue)
        if (mFlipped) {
            setStateFlipped(false)
        }
    } else {
        this.mBackView.setRotationY(-180 * (1f - value))
        this.mBackView.setScaleX(scaleValue)
        this.mBackView.setScaleY(scaleValue)
        if (!mFlipped) {
            setStateFlipped(true)
        }
    }
}

private fun setStateFlipped(flipped: Boolean) {
    mFlipped = flipped
    this.mFrontView.setVisibility(if (flipped) View.GONE else View.VISIBLE)
    this.mBackView.setVisibility(if (flipped) View.VISIBLE else View.GONE)
}
}
*/
