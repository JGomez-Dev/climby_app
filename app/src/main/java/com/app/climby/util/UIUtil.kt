package com.app.climby.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.app.climby.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

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

    //Barra de navegacion, Item foto de usuario
    fun changeItemWiseTextProperties(menu: Menu, context: Context) {
        Glide.with(context)
            .asBitmap()
            .circleCrop()
            .load(Commons.userSession?.photo)
            .error(R.drawable.ic_baseline_person_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>(100, 100) {
                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    menu.getItem(2).icon = BitmapDrawable(context.resources, resource)
                }
            })
    }

    //Teclado
    fun showKeyboard(context: Context) {
        val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
    fun hideKeyboard(context: Context) {
        val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
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