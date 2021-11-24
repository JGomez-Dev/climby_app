package com.example.climby.utils

import android.annotation.SuppressLint
import android.content.Context
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
                    formattedDate = "ENE"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "02" -> {
                    formattedDate = "FEB"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "03" -> {
                    formattedDate = "MAR"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "04" -> {
                    formattedDate = "ABR"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "05" -> {
                    formattedDate = "MAY"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "06" -> {
                    formattedDate = "JUN"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "07" -> {
                    formattedDate = "JUL"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "08" -> {
                    formattedDate = "AGO"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "09" -> {
                    formattedDate = "SEP"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "10" -> {
                    formattedDate = "OCT"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "11" -> {
                    formattedDate = "NOV"
                }
                departureString.split("-".toRegex()).toTypedArray()[1] == "12" -> {
                    formattedDate = "DIC"
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
    }



}