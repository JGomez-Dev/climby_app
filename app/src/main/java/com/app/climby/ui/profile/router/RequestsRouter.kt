package com.app.climby.ui.profile.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.profile.RequestsActivity
import com.app.climby.util.From
import com.app.climby.util.base.BaseActivityRouter

class RequestsRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, RequestsActivity::class.java)

    fun intent(activity: Context, trip: TripModel, from: String?): Intent {
        val intent = intent(activity)
        intent.putExtra("trip", trip)
        intent.putExtra("from", from)
        return intent
    }

    fun launch(activity: Context, trip: TripModel, from: From?) {
        activity.startActivity(intent(activity, trip, from?.status))
        if (from == null)
            (activity as Activity).overridePendingTransition(0, R.anim.slide_in_down)
        else
            (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}