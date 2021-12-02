package com.example.climby.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.ActivityRequestsBinding
import com.example.climby.ui.profile.adapter.RequestAdapter
import com.example.climby.ui.profile.viewmodel.RequestsViewModel
import com.example.climby.utils.Commons

class RequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestsBinding
    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var requestAdapter: RequestAdapter

    private var trip: TripModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestsViewModel = ViewModelProvider(this).get(RequestsViewModel::class.java)
        binding = ActivityRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            onBackPressed()
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
        requestAdapter = RequestAdapter(trip?.bookings ?: emptyList(), this)
        binding.RVRequest.adapter = requestAdapter
        requestAdapter.setOnClickListener(object : RequestAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onClickContact(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onClickRefuse(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onClickAcept(position: Int) {
                TODO("Not yet implemented")
            }
        })
    }

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
