package com.app.climby.ui.profile.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.ui.profile.EditProfileActivity
import com.app.climby.util.base.BaseActivityRouter

class EditProfileRouter : BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, EditProfileActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
    
}