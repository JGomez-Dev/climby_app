package com.example.climby.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.ActivityRequestsBinding
import com.example.climby.ui.profile.adapter.RequestAdapter
import com.example.climby.ui.profile.viewmodel.RequestsViewModel
import com.example.climby.utils.Commons
import com.example.climby.utils.ReservationStatus
import com.example.climby.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestsBinding
    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var requestAdapter: RequestAdapter
    private val acceptedBookingList: MutableList<BookingModel> = arrayListOf()

    private var trip: TripModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestsViewModel = ViewModelProvider(this).get(RequestsViewModel::class.java)
        binding = ActivityRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("profile", true)
            }
            startActivity(intent)
            overridePendingTransition(0, R.anim.slide_out_right)
        }
    }



    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        trip?.type?.name?.let { setPhotoTrip(it) }
        binding.TVType.text = trip?.type?.name + " en"
        binding.TVPlaceDate.text = trip?.site?.name + ", " + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        binding.RVRequest.layoutManager = LinearLayoutManager(this)
        trip?.bookings?.forEach {
            if ((it.status == ReservationStatus.ACCEPTED.status) || (it.status == ReservationStatus.UNANSWERED.status)) {
                acceptedBookingList.add(it)
            }
        }
        requestAdapter = RequestAdapter(acceptedBookingList, this)
        binding.RVRequest.adapter = requestAdapter
        requestAdapter.setOnClickListener(object : RequestAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Glide.with(applicationContext).load(trip?.bookings?.get(position)?.passenger?.photo).error(R.mipmap.user).into(binding.CVBackgroundRequest)
                binding.FLBackgroundRequest.isVisible = !binding.FLBackgroundRequest.isVisible
            }

            override fun onClickRefuse(position: Int) {
                /*showRefuseTripActivity(position)*/
                showDialogs(position)
            }

            override fun onClickContact(position: Int) {
                sendWhatsApp(position)
            }

            override fun onClickAcept(position: Int) {
                updateBooking(BookingModel(trip?.bookings?.get(position)?.id!!, trip?.bookings?.get(position)?.passenger , trip?.id!!, true, trip?.bookings?.get(position)?.valuationStatus, trip?.bookings?.get(position)?.date),"accepted", trip!!)
            }
        })
        binding.FLBackgroundRequest.setOnClickListener {
            binding.FLBackgroundRequest.isVisible = !binding.FLBackgroundRequest.isVisible
        }
    }

    private fun sendWhatsApp(position: Int) {
        val url = "https://api.whatsapp.com/send?phone=+34 " + trip?.bookings?.get(position)?.passenger?.phone.toString() + "&text=!Hola " + trip?.bookings?.get(position)?.passenger?.name?.split(" ")?.get(0).toString() + "! soy " + trip?.driver?.name?.split(" ")?.get(0).toString() + " gracias por unirte al viaje de " + trip?.site?.name
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_VIEW
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        sendIntent.type = "text/plain"
        sendIntent.data = Uri.parse(url)
        startActivity(sendIntent)
    }

    private fun showDialogs(position: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.text_sure)
            .setMessage(R.string.text_contact)
            .setNegativeButton(R.string.cancel) { view, _ ->
                Toast.makeText(this,"Cancelar",Toast.LENGTH_SHORT ).show()
                view.dismiss()
            }
            .setPositiveButton(R.string.text_delete) { view, _ ->
                updateBooking(BookingModel(trip?.bookings?.get(position)?.id!!,  trip?.bookings?.get(position)?.passenger , trip?.id!!, null, trip?.bookings?.get(position)?.valuationStatus, trip?.bookings?.get(position)?.date), "refuse", trip!!)
                requestAdapter.deleteItem(position)
                view.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }



    private fun updateBooking(bookingModel: BookingModel, request: String, trip: TripModel ) {
        requestsViewModel.updateBooking(bookingModel, request, trip,  this.applicationContext, this)
    }

    /*private fun showRefuseTripActivity(position: Int) {
        val intent = Intent(this, RefuseTripActivity::class.java).apply {
            putExtra("booking", trip?.bookings?.get(position))
            putExtra("trip", trip)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        finish()
    }*/

    private fun setPhotoTrip(type: String) {
        when (type) {
            "Boulder" -> {
                Glide.with(this).load(R.mipmap.boulder).error(R.mipmap.default_picture).into(binding.IVPlace)
            }
            "Deportiva" -> {
                Glide.with(this).load(R.mipmap.lead).error(R.mipmap.default_picture).into(binding.IVPlace)
            }
            "Rocódromo" -> {
                Glide.with(this).load(R.mipmap.gym).error(R.mipmap.default_picture).into(binding.IVPlace)
            }
            "Clásica" -> {
                Glide.with(this).load(R.mipmap.trad).error(R.mipmap.default_picture).into(binding.IVPlace)
            }
            else -> {
                Glide.with(this).load(R.mipmap.default_picture).error(R.mipmap.default_picture).into(binding.IVPlace)
            }
        }
    }
}
