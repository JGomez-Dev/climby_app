package com.app.climby.ui.profile.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.profile.EditTripActivity
import com.app.climby.util.base.BaseActivityRouter

class EditTripRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, EditTripActivity::class.java)

    fun intent(activity: Context, trip: TripModel, school: String?): Intent {
        val intent = intent(activity)
        intent.putExtra("trip", trip)
        intent.putExtra("school", school)
        return intent
    }

    fun launch(activity: Context, trip: TripModel, school: String?) {
        activity.startActivity(intent(activity, trip, school))
        if(school == null)
            (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        else
            (activity as Activity).overridePendingTransition(0, R.anim.slide_in_down)
    }

}