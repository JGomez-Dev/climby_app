package com.app.climby.ui.publish.router

import android.content.Context
import android.content.Intent
import com.app.climby.ui.publish.WhatPlaceActivity
import com.app.climby.util.base.BaseActivityRouter

class WhatPlaceRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, WhatPlaceActivity::class.java)
}