package com.app.climby.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
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
import com.app.climby.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object UIUtil {

    fun animateHand(ivHandEmpty: ImageView) {
        val anim = ObjectAnimator.ofFloat(ivHandEmpty, "translationY", 0f, 50f)
        anim.duration = UIConstants.ANIMATE
        anim.repeatCount = Animation.INFINITE;
        anim.repeatMode = ValueAnimator.REVERSE;
        anim.start()
    }

     fun animateConnection(tvDontService: TextView) {
        val anim = ObjectAnimator.ofFloat(tvDontService, "translationY", 50f, 0f)
        anim.duration = UIConstants.ANIMATE
        anim.repeatCount = Animation.ABSOLUTE
        anim.start()
    }

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

    fun showKeyboard(context: Context) {
        val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
    
    fun hideKeyboard(context: Context) {
        val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun getExperience(userExperience: String, context: Context): String {
        when (userExperience) {
            UserExperience.BEGINNER.status -> return context.getString(R.string.edit_profile_beginner)
            UserExperience.MEDIUM.status -> return context.getString(R.string.edit_profile_intermediate)
            UserExperience.ADVANCED.status -> return context.getString(R.string.edit_profile_experienced)
        }
        return context.getString(R.string.edit_profile_beginner)
    }

    fun setPhotoTrip(type: String, context: Context, image: ImageView) {
        when (type) {
            Types.BOULDER.status -> {
                Glide.with(context).load(R.mipmap.boulder).error(R.mipmap.default_picture).into(image)
            }
            Types.LEAD.status -> {
                Glide.with(context).load(R.mipmap.lead).error(R.mipmap.default_picture).into(image)
            }
            Types.ROCODROMO.status -> {
                Glide.with(context).load(R.mipmap.gym).error(R.mipmap.default_picture).into(image)
            }
            Types.CLASSIC.status -> {
                Glide.with(context).load(R.mipmap.trad).error(R.mipmap.default_picture).into(image)
            }
            else -> {
                Glide.with(context).load(R.mipmap.default_picture).error(R.mipmap.default_picture).into(image)
            }
        }
    }
}