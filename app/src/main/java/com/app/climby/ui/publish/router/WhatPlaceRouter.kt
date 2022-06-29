package com.app.climby.ui.publish.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.publish.WhatPlaceActivity
import com.app.climby.util.From
import com.app.climby.util.base.BaseActivityRouter

class WhatPlaceRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, WhatPlaceActivity::class.java)

    fun intent(activity: Context, school: String, from: String, trip: TripModel?): Intent {
        val intent = intent(activity)
        if (trip != null)
            intent.putExtra("trip", trip)
        intent.putExtra("from", from)
        intent.putExtra("school", school)
        return intent
    }

    fun launch(activity: Context, school: String, from: From, trip: TripModel?) {
        activity.startActivity(intent(activity, school, from.status, trip))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}