package com.app.climby.view.router

import android.content.Context
import android.content.Intent
import com.app.climby.util.base.BaseActivityRouter
import com.app.climby.view.activity.AuthActivity

class AuthRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, AuthActivity::class.java)
}