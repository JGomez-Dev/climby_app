package com.app.climby.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.ActivityResumeTripBinding
import com.app.climby.ui.profile.adapter.ResumeTripAdapter
import com.app.climby.ui.profile.viewmodel.ResumeTripViewModel
import com.app.climby.util.Commons
import com.app.climby.util.From
import com.app.climby.util.UIUtil
import com.app.climby.view.router.MainRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumeTripBinding
    private lateinit var resumeTripViewModel: ResumeTripViewModel
    private lateinit var resumeTripAdapter: ResumeTripAdapter

    private var trip: TripModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resumeTripViewModel = ViewModelProvider(this)[ResumeTripViewModel::class.java]
        binding = ActivityResumeTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.IVBack.setOnClickListener {
            goToMainActivity()
        }

        resumeTripViewModel.tripModel.observe(this, Observer {
            trip = it
            init()
        })
    }

    private fun markMessagesAsRead(trip: TripModel?) {
        resumeTripViewModel.markMessagesAsRead(trip)
    }

    private fun goToMainActivity() {
        MainRouter().launch(this, null, From.PROFILE, isEdit = false)
        finish()
    }

    private fun getData() {
        val bundle = intent.extras
        if (bundle != null) {
            trip = bundle.getParcelable("trip")
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
        trip?.type?.name?.let { UIUtil.setPhotoTrip(it, this, binding.IVSiteQualifyAttendees) }
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
}