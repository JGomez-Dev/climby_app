package com.example.climby.ui.profile

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.databinding.FragmentComingOutingsBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.profile.viewmodel.ComingOutingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingOutingsFragment : Fragment() {

    private lateinit var binding: FragmentComingOutingsBinding
    private lateinit var comingOutingsViewModel: ComingOutingsViewModel
    private lateinit var discoverAdapter: DiscoverAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        comingOutingsViewModel = ViewModelProvider(this).get(ComingOutingsViewModel::class.java)
        binding = FragmentComingOutingsBinding.inflate(layoutInflater)
        val view: View = binding.root

        binding.RVTrips.layoutManager = LinearLayoutManager(activity)
        comingOutingsViewModel.tripsModel.observe(viewLifecycleOwner,  {
            if(it.isNullOrEmpty()){
                binding.CLTripsEmpty.isVisible = true
                binding.RVTrips.isVisible = false
                moveHand()
            }else{
                binding.CLTripsEmpty.isVisible = false
                binding.RVTrips.isVisible = true
                discoverAdapter = DiscoverAdapter(it, requireContext())
                binding.RVTrips.adapter = discoverAdapter
                discoverAdapter.setOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        //loadActivity(it[position])
                    }

                    override fun onClickAddMe(position: Int) {

                    }

                    override fun onClickRemoveMe(_it: BookingModel, position: Int) {

                    }
                })
            }
        })
        comingOutingsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBMyOutings.isVisible = it
        })

        comingOutingsViewModel.getMyTrips()


        return view
    }


    private fun moveHand(){
        val anim = ObjectAnimator.ofFloat(binding.IVHandEmpty, "translationY", 0f, 50f)
        anim.duration = 1000
        anim.repeatCount = Animation.INFINITE;
        anim.repeatMode = ValueAnimator.REVERSE;

        anim.start()
    }
}