package com.app.climby.utils

import android.app.Activity
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.app.climby.R
import com.android.volley.toolbox.JsonObjectRequest
import kotlin.Throws
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.app.climby.data.model.trip.TripModel
import org.json.JSONException
import java.util.HashMap

class FcmNotificationsSender(var userFcmToken: String, private var title: String, var intent: String, private var id: String, private var to: String, var body: String, mContext: Context, var mActivity: Activity) {
    private var requestQueue: RequestQueue? = null
    //private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val postUrl = "https://fcm.googleapis.com/fcm/send"
    private val fcmServerKey = mContext.getString(R.string.fcmServerKey)
    fun sendNotifications() {
        requestQueue = Volley.newRequestQueue(mActivity)
        val mainObj = JSONObject()
        try {
            mainObj.put("to", userFcmToken)
            val notiObject = JSONObject()
            notiObject.put("title", title)
            notiObject.put("body", body)
            notiObject.put("icon", R.drawable.icon_app) // enter icon that exists in drawable only
            notiObject.put("click_action", intent) //Actividad que se abre cuando hacemos click en la notifiacion

            mainObj.put("notification", notiObject)

            val extraObject = JSONObject()
            extraObject.put("push", true)
            extraObject.put("to", to)
            extraObject.put("id", id)

            mainObj.put("data", extraObject)

            val request: JsonObjectRequest = object : JsonObjectRequest(Method.POST, postUrl, mainObj, Response.Listener {
                // code run is got response
            }, Response.ErrorListener {
                // code run is got error
            }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val header: MutableMap<String, String> = HashMap()
                    header["content-type"] = "application/json"
                    header["authorization"] = "key=$fcmServerKey"
                    return header
                }
            }
            requestQueue!!.add(request)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}