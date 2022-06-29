package com.app.climby.ui.profile.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.profile.ResumeTripActivity
import com.app.climby.util.base.BaseActivityRouter

class ResumeTripRouter : BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, ResumeTripActivity::class.java)

    fun intent(activity: Context, trip: TripModel): Intent {
        val intent = intent(activity)
        intent.putExtra("trip", trip)
        return intent
    }

    fun launch(activity: Context, trip: TripModel) {
        activity.startActivity(intent(activity, trip))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
