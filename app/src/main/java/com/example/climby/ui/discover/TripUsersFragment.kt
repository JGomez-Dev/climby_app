package com.example.climby.ui.discover

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.climby.databinding.FragmentTripUsersBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.TripUsersViewModel
import com.example.climby.utils.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TripUsersFragment : Fragment(), IOnBackPressed {

    private lateinit var binding: FragmentTripUsersBinding
    private lateinit var tripUsersViewModel: TripUsersViewModel
    private lateinit var discoverAdapter: DiscoverAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tripUsersViewModel = ViewModelProvider(this).get(TripUsersViewModel::class.java)
        binding = FragmentTripUsersBinding.inflate(layoutInflater)

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }

        return binding.root
    }

    override fun onBackPressed() {
        val activity: Activity? = activity
        activity?.onBackPressed()
    }
}