package com.app.climby.view.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.app.climby.R
import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.util.From
import com.app.climby.util.base.BaseActivityRouter
import com.app.climby.view.activity.MainActivity

class MainRouter  : BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, MainActivity::class.java)

    fun intent(activity: Context, province: String?): Intent {
        val intent = intent(activity)
        if (!province.isNullOrEmpty())
            intent.putExtra("province", province)
        return intent
    }

    fun intent(activity: Context, school: String, province: Int?, type: Int?, date: String?, dateWithoutFormat: String?, place: Int?, from: String): Intent {
        val intent = intent(activity)
        intent.putExtra("school", school)
        intent.putExtra("province", province)
        intent.putExtra("type", type)
        intent.putExtra("date", date)
        intent.putExtra("dateWithoutFormat", dateWithoutFormat)
        intent.putExtra("place", place)
        intent.putExtra("from", from)
        return intent
    }

    fun launch(activity: Context, province: ProvinceModel) {
        activity.startActivity(intent(activity, province.name))
        (activity as Activity).overridePendingTransition(0, R.anim.slide_in_down)
    }

    fun launch(activity: Context, school: String, province: Int?, type: Int?, date: String?, dateWithoutFormat: String?, place: Int?, from: From) {
        activity.startActivity(intent(activity, school, province, type, date, dateWithoutFormat, place, from.status))
        (activity as Activity).overridePendingTransition(0, R.anim.slide_in_down)
    }

}