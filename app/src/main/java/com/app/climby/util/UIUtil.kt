package com.app.climby.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.climby.R

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

    fun showAlert(context: Context, title: String, message: String, positive: String, positiveAction: (() -> Unit)? = null, negative: String? = null, destroy: Boolean = false) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positive) { _, _ ->
            positiveAction?.let {
                it()
            }
        }
        negative?.let {
            builder.setNegativeButton(it) { _, _ ->
                // Do nothing
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, if (destroy) R.color.red_delete else R.color.white))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.black))
    }
}