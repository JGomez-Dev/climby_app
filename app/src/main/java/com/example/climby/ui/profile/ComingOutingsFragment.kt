package com.example.climby.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climby.databinding.FragmentComingOutingsBinding
import com.example.climby.ui.profile.viewmodel.ComingOutingsViewModel

class ComingOutingsFragment : Fragment() {

    private lateinit var binding: FragmentComingOutingsBinding
    private lateinit var comingOutingsViewModel: ComingOutingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        comingOutingsViewModel = ViewModelProvider(this).get(ComingOutingsViewModel::class.java)
        binding = FragmentComingOutingsBinding.inflate(layoutInflater)
        val view: View = binding.root

        comingOutingsViewModel.getNextTrips()

        comingOutingsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBComingOutings.isVisible = it
        })

        return view
    }
}