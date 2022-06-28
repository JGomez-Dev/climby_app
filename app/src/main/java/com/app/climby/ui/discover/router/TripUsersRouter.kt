package com.app.climby.ui.discover.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.discover.TripUsersActivity
import com.app.climby.util.base.BaseActivityRouter

class TripUsersRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, TripUsersActivity::class.java)

    fun intent(activity: Context, trip: TripModel): Intent {
        val intent = intent(activity)
        intent.putExtra("trip", trip)
        intent.putExtra("from", "discover")
        return intent
    }

    fun launch(activity: Context, trip: TripModel) {
        activity.startActivity(intent(activity, trip))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    /*val intent = Intent(it, TripUsersActivity::class.java).apply {
                putExtra("trip", trip)
                putExtra("from", "discover")
            }
            startActivity(intent)
            it.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)*/

}