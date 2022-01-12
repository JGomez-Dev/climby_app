package com.example.climby.ui.profile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.ActivityRefuseTripBinding
import com.example.climby.ui.profile.viewmodel.RefuseTripViewModel
import com.example.climby.utils.Commons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefuseTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefuseTripBinding
    private lateinit var refuseTripViewModel: RefuseTripViewModel

    private var booking: BookingModel? = null
    private var trip: TripModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refuseTripViewModel = ViewModelProvider(this).get(RefuseTripViewModel::class.java)
        binding = ActivityRefuseTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.IVBack.setOnClickListener {
            /*onBackPressed()*/
            val intent = Intent(this, RequestsActivity::class.java).apply {
                putExtra("trip", trip)
            }
            startActivity(intent)
            overridePendingTransition(0, R.anim.slide_in_down)
            finish()
        }

        binding.BTRefuseTrip.setOnClickListener {
            updateBooking(BookingModel(booking?.id!!, booking?.passenger , trip?.id!!, null, booking?.valuationStatus, booking?.date))

            /*deleteBooking(booking)*/
        }
    }

    private fun updateBooking(bookingModel: BookingModel) {
        Commons.sendNotification(
            bookingModel.passenger?.token!!,
            trip?.driver?.name!!.split(" ")[0] + " ha rechazado tu solicitud",
            "OPEN_MainActivity",
            "myOutigsFragment",
            trip?.driver?.name!!.split(" ")[0]  + " ha rechazado tu solicitud para la salida a " + trip?.site?.name + " el " + trip?.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip?.departure.toString()),
            applicationContext,
            this
        )
        refuseTripViewModel.updateBooking(bookingModel)
    }

    /*private fun deleteBooking(booking: BookingModel?) {
        refuseTripViewModel.deleteBooking(booking)
    }*/

    private fun getData() {
        val bundle = intent.extras
        booking = bundle?.getParcelable("booking")
        trip = bundle?.getParcelable("trip")
    }
}