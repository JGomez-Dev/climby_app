package com.app.climby.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.ActivityResumeTripBinding
import com.app.climby.ui.profile.adapter.ResumeTripAdapter
import com.app.climby.ui.profile.viewmodel.ResumeTripViewModel
import com.app.climby.util.Commons
import com.app.climby.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumeTripBinding
    private lateinit var resumeTripViewModel: ResumeTripViewModel
    private lateinit var resumeTripAdapter: ResumeTripAdapter

    private var trip: TripModel? = null
    private var from: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resumeTripViewModel = ViewModelProvider(this)[ResumeTripViewModel::class.java]
        binding = ActivityResumeTripBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getData()

        /*binding.IVBack.setOnClickListener {
            showMainActivity()
        }*/



        binding.IVBack.setOnClickListener {
            showMainActivity()
        }

        resumeTripViewModel.tripModel.observe(this, Observer {
            trip = it
            init()
        })
    }

    private fun markMessagesAsRead(trip: TripModel?) {
        resumeTripViewModel.markMessagesAsRead(trip)
    }

    private fun showMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("from", "profile")
        }
        startActivity(intent)
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
    }


    private fun getData() {
        val bundle = intent.extras
        if (bundle != null) {
            trip = bundle.getParcelable("trip")
            from = bundle.getString("from")
            val idTrip = bundle.getInt("idTrip")
            if (idTrip != 0) {
                resumeTripViewModel.getTripById(idTrip)
            } else {
                init()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        trip?.type?.name?.let { setPhotoTrip(it) }
        binding.TVTypeQualifyAttendees.text = trip?.type?.name + " en"
        binding.TVSiteQualifyAttendees.text = trip?.site?.name + ", \n" + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        binding.RVResumenTrip.layoutManager = LinearLayoutManager(this)
        val bookingWithMessage: MutableList<BookingModel> = arrayListOf()
        trip?.bookings!!.forEach {
            if (it.message != null) {
                bookingWithMessage.add(it)
            }
        }
        if (!trip?.bookings.isNullOrEmpty()) {
            resumeTripAdapter = ResumeTripAdapter(bookingWithMessage, this)
            binding.RVResumenTrip.adapter = resumeTripAdapter
        }
        markMessagesAsRead(trip)
    }

    private fun setPhotoTrip(type: String) {
        when (type) {
            "Boulder" -> {
                Glide.with(this).load(R.mipmap.boulder).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            "Deportiva" -> {
                Glide.with(this).load(R.mipmap.lead).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            "Rocódromo" -> {
                Glide.with(this).load(R.mipmap.gym).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            "Clásica" -> {
                Glide.with(this).load(R.mipmap.trad).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
            else -> {
                Glide.with(this).load(R.mipmap.default_picture).error(R.mipmap.default_picture).into(binding.IVSiteQualifyAttendees)
            }
        }
    }
}