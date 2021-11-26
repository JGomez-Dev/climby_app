package com.example.climby.ui.discover

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ActivityTripUsersBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.TripUsersViewModel
import com.example.climby.utils.Commons
import com.example.climby.utils.IOnBackPressed
import com.example.climby.utils.UserExperience
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripUsersActivity : AppCompatActivity(), IOnBackPressed {

    private lateinit var binding: ActivityTripUsersBinding
    private lateinit var tripUsersViewModel: TripUsersViewModel
    private lateinit var discoverAdapter: DiscoverAdapter
    private var departure: String? = null
    private var trip: TripModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripUsersViewModel = ViewModelProvider(this).get(TripUsersViewModel::class.java)
        binding = ActivityTripUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }

        getData()
        init()

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
    }

    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
        Log.i("trip", trip.toString())
    }

    private fun getExperince(userExperience: String): String {
        when (userExperience) {
            UserExperience.BEGINNER.status -> return "Principiante"
            UserExperience.MEDIUM.status -> return "Intermedio"
            UserExperience.ADVANCED.status -> return "Experimentado"
        }
        return "Principiante"
    }

}