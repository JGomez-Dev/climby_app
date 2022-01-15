package com.example.climby.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.message.MessageModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.ActivityResumeTripBinding
import com.example.climby.ui.profile.adapter.RequestAdapter
import com.example.climby.ui.profile.adapter.ResumeTripAdapter
import com.example.climby.ui.profile.viewmodel.ResumeTripViewModel
import com.example.climby.utils.Commons
import com.example.climby.utils.ReservationStatus
import com.example.climby.view.activity.MainActivity

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
        init()

        binding.IVBack.setOnClickListener {
            showMainActivity()
        }

    }

    private fun showMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("from", "profile")
        }
        startActivity(intent)
        overridePendingTransition(0, R.anim.slide_out_right)
    }


    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        trip?.type?.name?.let { setPhotoTrip(it) }
        binding.TVTypeQualifyAttendees.text = trip?.type?.name + " en"
        binding.TVSiteQualifyAttendees.text = trip?.site?.name + ", \n" + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        binding.RVResumenTrip.layoutManager = LinearLayoutManager(this)
        /* TODO ojo al manojo, esto solo es un test*/
        /*trip?.bookings?.forEach { it ->
            val message = MessageModel(false, "Gracias por organizar esta salida. Ayer fué un día fantástico y aprendimos mogollón. La próxima vez llevo comida y no te dejo sin bocata.")
            it.message = message
        }*/
        if(!trip?.bookings.isNullOrEmpty()){
            resumeTripAdapter = ResumeTripAdapter(trip?.bookings!!, this)
            binding.RVResumenTrip.adapter = resumeTripAdapter
        }

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