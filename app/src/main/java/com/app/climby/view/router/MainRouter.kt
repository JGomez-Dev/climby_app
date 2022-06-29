package com.app.climby.view.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.ui.publish.WhatPlaceActivity
import com.app.climby.util.From
import com.app.climby.util.base.BaseActivityRouter
import com.app.climby.view.activity.MainActivity

class MainRouter  : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, MainActivity::class.java)

}