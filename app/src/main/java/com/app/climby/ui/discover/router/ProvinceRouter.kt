package com.app.climby.ui.discover.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.discover.ProvinceActivity
import com.app.climby.util.From
import com.app.climby.util.base.BaseActivityRouter

class ProvinceRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, ProvinceActivity::class.java)

    fun intent(activity: Context, province: String): Intent {
        val intent = intent(activity)
        intent.putExtra("province", province)
        return intent
    }

    fun launch(activity: Context, province: String) {
        activity.startActivity(intent(activity, province))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

}