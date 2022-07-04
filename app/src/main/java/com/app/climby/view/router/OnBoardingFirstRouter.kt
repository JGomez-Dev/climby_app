package com.app.climby.view.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.util.base.BaseActivityRouter
import com.app.climby.view.activity.OnBoardingFirstActivity

class OnBoardingFirstRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, OnBoardingFirstActivity::class.java)

    fun intent(activity: Context, email: String?, photoUrl: String?, displayName: String?, phone: String?): Intent {
        val intent = intent(activity)
        intent.putExtra("email", email)
        intent.putExtra("photoUrl", photoUrl)
        intent.putExtra("displayName", displayName)
        if (phone != null)
            intent.putExtra("phone", phone)
        return intent
    }

    fun launch(activity: Context, email: String?, photoUrl: String?, displayName: String?, phone: String?) {
        activity.startActivity(intent(activity, email, photoUrl, displayName, phone))
        if (phone == null)
            (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        else
            (activity as Activity).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}