package com.app.climby.ui.discover.router

import android.content.Context
import android.content.Intent
import com.app.climby.ui.discover.ProvinceActivity
import com.app.climby.util.base.BaseActivityRouter

class ProvinceRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, ProvinceActivity::class.java)
}