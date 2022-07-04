package com.app.climby.view.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.util.base.BaseActivityRouter
import com.app.climby.view.activity.OnBoardingSecondActivity

class OnBoardingSecondRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, OnBoardingSecondActivity::class.java)

    fun intent(activity: Context, email: String?, photoUrl: String?, displayName: String?, phone: String?): Intent {
        val intent = intent(activity)
        intent.putExtra("email", email)
        intent.putExtra("photoUrl", photoUrl)
        intent.putExtra("displayName", displayName)
        intent.putExtra("phone", phone)
        return intent
    }

    fun launch(activity: Context, email: String?, photoUrl: String?, displayName: String?, phone: String?, isFromAuth: Boolean) {
        activity.startActivity(intent(activity, email, photoUrl, displayName, phone))
        if (isFromAuth)
            (activity as Activity).overridePendingTransition(0, 0)
        else
            (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }
}