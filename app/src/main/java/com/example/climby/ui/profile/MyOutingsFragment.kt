package com.example.climby.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climby.databinding.FragmentMyOutingsBinding
import com.example.climby.ui.profile.viewmodel.MyOutingsViewModel

class MyOutingsFragment : Fragment() {

    private lateinit var binding: FragmentMyOutingsBinding
    private lateinit var myOutingsViewModel: MyOutingsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myOutingsViewModel = ViewModelProvider(this).get(MyOutingsViewModel::class.java)
        binding = FragmentMyOutingsBinding.inflate(layoutInflater)
        val view: View = binding.root
        myOutingsViewModel.getMyTrips()


        myOutingsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBMyOutings.isVisible = it
        })

        return view
    }
}