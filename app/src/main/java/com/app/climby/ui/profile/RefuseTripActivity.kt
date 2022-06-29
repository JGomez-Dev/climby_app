package com.app.climby.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.ActivityRefuseTripBinding
import com.app.climby.ui.profile.router.RequestsRouter
import com.app.climby.ui.profile.viewmodel.RefuseTripViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefuseTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefuseTripBinding
    private lateinit var refuseTripViewModel: RefuseTripViewModel

    private var booking: BookingModel? = null
    private lateinit var trip: TripModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refuseTripViewModel = ViewModelProvider(this)[RefuseTripViewModel::class.java]
        binding = ActivityRefuseTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.IVBack.setOnClickListener {
            onBackPressed()

        }

        binding.BTRefuseTrip.setOnClickListener {
            updateBooking(BookingModel(booking?.id!!, booking?.passenger , trip.id, null, booking?.valuationStatus, booking?.date, booking?.message ))

            /*deleteBooking(booking)*/
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goRequestsActivity(trip)
    }

    private fun goRequestsActivity(trip: TripModel) {
        RequestsRouter().launch(this, trip, null)
        finish()
        /*val intent = Intent(this, RequestsActivity::class.java).apply {
            putExtra("trip", trip)
        }
        startActivity(intent)
        overridePendingTransition(0, R.anim.slide_in_down)*/

    }

    private fun updateBooking(bookingModel: BookingModel) {
       /* Commons.sendNotification(
            bookingModel.passenger?.token!!,
            trip?.driver?.name!!.split(" ")[0] + " ha rechazado tu solicitud",
            "OPEN_MainActivity",
            "myOutigsFragment",
            "myOutigsFragment",
            trip?.driver?.name!!.split(" ")[0]  + " ha rechazado tu solicitud para la salida a " + trip?.site?.name + " el " + trip?.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip?.departure.toString()),
            applicationContext,
            this
        )*/
        refuseTripViewModel.updateBooking(bookingModel)
    }

    /*private fun deleteBooking(booking: BookingModel?) {
        refuseTripViewModel.deleteBooking(booking)
    }*/

    private fun getData() {
        val bundle = intent.extras
        booking = bundle?.getParcelable("booking")
        trip = bundle?.getParcelable("trip")!!
    }
}