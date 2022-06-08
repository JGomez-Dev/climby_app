package com.example.climby.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Button
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

        fun sendNotification(token: String, title: String, intent: String, id: String, to: String, body: String, context: Context, activity: Activity){
            val fcmNotificationsSender = FcmNotificationsSender(token, title, intent, id, to, body, context, activity)
            fcmNotificationsSender.sendNotifications()
        }

        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }

                    }
                }
            }

            return result
        }

    }



}