package com.example.climby.ui.profile

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
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
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.FragmentMyOutingsBinding
import com.example.climby.ui.profile.adapter.DiscoverProfileAdapter
import com.example.climby.ui.profile.viewmodel.MyOutingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOutingsFragment : Fragment() {

    private lateinit var binding: FragmentMyOutingsBinding
    private lateinit var myOutingsViewModel: MyOutingsViewModel
    private lateinit var discoverAdapterProfile: DiscoverProfileAdapter

    private lateinit var listTrip: List<TripModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myOutingsViewModel = ViewModelProvider(this)[MyOutingsViewModel::class.java]
        binding = FragmentMyOutingsBinding.inflate(layoutInflater)
        val view: View = binding.root

        binding.RVTrips.layoutManager = LinearLayoutManager(activity)
        myOutingsViewModel.tripsModel.observe(viewLifecycleOwner,  {
            if(it.isNullOrEmpty()){
                binding.CLTripsEmpty.isVisible = true
                binding.RVTrips.isVisible = false
                moveHand()
            }else{
                /*binding.CLTripsEmpty.isVisible = false*/
                binding.RVTrips.isVisible = true
                discoverAdapterProfile = DiscoverProfileAdapter(it, requireContext())
                binding.RVTrips.adapter = discoverAdapterProfile
                discoverAdapterProfile.setOnItemClickListener(object : DiscoverProfileAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        //loadActivity(it[position])
                    }

                    override fun onItemEdit(position: Int) {
                        showEditTripActivity(it[position])
                    }

                    override fun onItemShowResume(position: Int) {
                        showResumeTripActivity(it[position])
                    }
                })
            }
        })
        myOutingsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBMyOutings.isVisible = it
        })

        myOutingsViewModel.getMyTrips()


        return view
    }

    private fun showResumeTripActivity(tripModel: TripModel) {
        val intent = Intent(activity, ResumeTripActivity::class.java).apply {
            putExtra("trip", tripModel)
        }
        startActivity(intent)
    }


    private fun showEditTripActivity(tripModel: TripModel) {
        val intent = Intent(activity, EditTripActivity::class.java).apply {
            putExtra("trip", tripModel)
        }
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    private fun moveHand(){
        val anim = ObjectAnimator.ofFloat(binding.IVHandEmpty, "translationY", 0f, 50f)
        anim.duration = 1000
        anim.repeatCount = Animation.INFINITE;
        anim.repeatMode = ValueAnimator.REVERSE;

        anim.start()
    }
}