package com.app.climby.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView

object UIUtil {

    //AnimateHand
    fun animateHand(ivHandEmpty: ImageView) {
        val anim = ObjectAnimator.ofFloat(ivHandEmpty, "translationY", 0f, 50f)
        anim.duration = UIConstants.ANIMATE
        anim.repeatCount = Animation.INFINITE;
        anim.repeatMode = ValueAnimator.REVERSE;
        anim.start()
    }

    //AnimateConnection
     fun animateConnection(tvDontService: TextView) {
        val anim = ObjectAnimator.ofFloat(tvDontService, "translationY", 50f, 0f)
        anim.duration = UIConstants.ANIMATE
        anim.repeatCount = Animation.ABSOLUTE
        anim.start()
    }
}