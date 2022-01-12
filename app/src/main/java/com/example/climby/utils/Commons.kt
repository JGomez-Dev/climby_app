package com.example.climby.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.icu.text.CaseMap
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel

class Commons {
    companion object {
        fun getDate(departureString: String): Any {
            var formattedDate = ""
            when {
                departureString.split("-".toRegex()).toTypedArray()[1] == "01" -> {
                    formattedDate = "Enero"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "02" -> {
                    formattedDate = "Febrero"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "03" -> {
                    formattedDate = "Marzo"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "04" -> {
                    formattedDate = "Abril"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "05" -> {
                    formattedDate = "Mayo"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "06" -> {
                    formattedDate = "Junio"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "07" -> {
                    formattedDate = "Julio"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "08" -> {
                    formattedDate = "Agosto"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "09" -> {
                    formattedDate = "Septiembre"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "10" -> {
                    formattedDate = "Octubre"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "11" -> {
                    formattedDate = "Noviembre"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "12" -> {
                    formattedDate = "Diciembre"
                }
            }
            return formattedDate
        }

        @SuppressLint("SetTextI18n")
        fun setTextButton(btRequest: Button, result: TripModel) {
            /*if(result.bookings.isNotEmpty()){
                btRequest.text = "Solicitar\n"+ (result.availablePlaces - result.bookings.size) +" plazas";
            }else{
                btRequest.text = "Solicitar\n"+ result.availablePlaces +" plazas";
            }*/
        }
        var userSession: UserModel? = null

        fun sendNotification( token: String, title: String, intent: String, extras: String, body: String, context: Context, activity: Activity){
            val fcmNotificationsSender = FcmNotificationsSender(token, title, intent, extras, body, context, activity)
            fcmNotificationsSender.sendNotifications()
        }
    }



}