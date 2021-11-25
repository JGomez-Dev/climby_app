package com.example.climby.ui.discover

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.climby.databinding.ActivityTripUsersBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.TripUsersViewModel
import com.example.climby.utils.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripUsersActivity  : AppCompatActivity(), IOnBackPressed {

    private lateinit var binding: ActivityTripUsersBinding
    private lateinit var tripUsersViewModel: TripUsersViewModel
    private lateinit var discoverAdapter: DiscoverAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripUsersViewModel = ViewModelProvider(this).get(TripUsersViewModel::class.java)
        binding = ActivityTripUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }


    }

}