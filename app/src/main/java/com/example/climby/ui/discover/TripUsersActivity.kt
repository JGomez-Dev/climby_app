package com.example.climby.ui.discover

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ActivityTripUsersBinding
import com.example.climby.ui.discover.adapter.TripUsersAdapter
import com.example.climby.ui.discover.viewmodel.TripUsersViewModel
import com.example.climby.utils.Commons
import com.example.climby.utils.IOnBackPressed
import com.example.climby.utils.ReservationStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripUsersActivity : AppCompatActivity(), IOnBackPressed {

    private lateinit var binding: ActivityTripUsersBinding
    private lateinit var tripUsersViewModel: TripUsersViewModel
    private lateinit var tripUsersAdapter: TripUsersAdapter
    private var trip: TripModel? = null
    private var userSession: UserModel = Commons.userSession!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripUsersViewModel = ViewModelProvider(this).get(TripUsersViewModel::class.java)
        binding = ActivityTripUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.TVVAdmin.text = trip?.driver?.name?.split(" ")?.get(0) ?: ""
        binding.TVExperience.text = trip?.driver?.experience
        binding.TVExitsAdmin.text = if (trip?.driver?.outings.toString() == "1") {
            trip?.driver?.outings.toString() + " salida"
        } else {
            trip?.driver?.outings.toString() + " salidas"
        }
        binding.TVSite.text = trip?.site?.name + ", " + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        Glide.with(this).load(trip?.driver?.photo).error(R.mipmap.user).into(binding.CIVAdmin)
        trip?.driver?.let { setStart(it)}
        if(trip?.bookings?.size == 0){
            binding.TVAssistants.visibility = View.GONE
        }
        binding.RVAssistants.layoutManager = LinearLayoutManager(this)
        tripUsersAdapter = TripUsersAdapter(acceptedBooking(), this)
        binding.RVAssistants.adapter = tripUsersAdapter
        tripUsersAdapter.setOnClickListener(object : TripUsersAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
               /* Glide.with(applicationContext).load(trip?.bookings?.get(position)?.passenger?.photo).error(R.mipmap.user).into(binding.CVBackgroundRequest)
                binding.FLBackgroundRequest.isVisible = !binding.FLBackgroundRequest.isVisible*/
            }

        })
    }

    private fun acceptedBooking(): MutableList<BookingModel> {
        val acceptedBookingList: MutableList<BookingModel> = arrayListOf()
        trip?.bookings?.forEach { it ->
            if (it.passenger?.id ?: 0 == userSession.id) {
                when (it.status) {
                    ReservationStatus.ACCEPTED.status -> {
                        acceptedBookingList.add(it)
                    }
                }
            } else {
                acceptedBookingList.add(it)
            }
        }
        return acceptedBookingList
    }

    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
    }

    private fun setStart(passenger: UserModel) {
        when {
            passenger.score > 2.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.star)
                binding.IVStart3.setImageResource(R.mipmap.star)
            }
            passenger.score in 2.25..2.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.star)
                binding.IVStart3.setImageResource(R.mipmap.medstart)
            }
            passenger.score in 1.75..2.25 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.star)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
            passenger.score in 1.25..1.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.medstart)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
            passenger.score in 0.75..1.25 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
            passenger.score <= 0.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.medstart)
                binding.IVStart2.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
        }
    }
}