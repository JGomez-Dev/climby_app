package com.app.climby.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.FragmentMyOutingsBinding
import com.app.climby.ui.profile.adapter.DiscoverProfileAdapter
import com.app.climby.ui.profile.router.EditTripRouter
import com.app.climby.ui.profile.router.ResumeTripRouter
import com.app.climby.ui.profile.viewmodel.MyOutingsViewModel
import com.app.climby.util.Commons
import com.app.climby.util.From
import com.app.climby.util.UIUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOutingsFragment : Fragment() {

    private lateinit var binding: FragmentMyOutingsBinding
    private lateinit var myOutingsViewModel: MyOutingsViewModel
    private lateinit var discoverAdapterProfile: DiscoverProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        myOutingsViewModel = ViewModelProvider(this)[MyOutingsViewModel::class.java]
        binding = FragmentMyOutingsBinding.inflate(layoutInflater)
        val view: View = binding.root

        if(!Commons.isInternetAvailable(requireContext().applicationContext)){
            binding.CLNotConnection.isVisible = true
        }else {
            binding.CLNotConnection.isVisible = false
            binding.RVTrips.layoutManager = LinearLayoutManager(activity)
            myOutingsViewModel.tripsModel.observe(viewLifecycleOwner) {tripList ->
                if (tripList.isNullOrEmpty()) {
                    binding.CLTripsEmpty.isVisible = true
                    binding.RVTrips.isVisible = false
                    //moveHand()
                    UIUtil.animateHand(binding.IVHandEmpty)
                } else {
                    /*binding.CLTripsEmpty.isVisible = false*/
                    binding.RVTrips.isVisible = true
                    activity?.let {
                        discoverAdapterProfile = DiscoverProfileAdapter(tripList, requireContext(), it, From.PROFILE)
                        binding.RVTrips.adapter = discoverAdapterProfile
                        discoverAdapterProfile.setOnItemClickListener(object : DiscoverProfileAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                //loadActivity(it[position])
                            }

                            override fun onItemEdit(position: Int) {
                                goToEditTripActivity(tripList[position])
                            }

                            override fun onItemShowResume(position: Int) {
                                goToResumeTripActivity(tripList[position])
                            }
                        })
                    }
                }
            }
            myOutingsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
                binding.PBMyOutings.isVisible = it
            })

            binding.pullToRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.primary))
            binding.pullToRefresh.setOnRefreshListener {
                myOutingsViewModel.getMyTrips()
                binding.pullToRefresh.isRefreshing = false
            }

            myOutingsViewModel.getMyTrips()

        }

        return view
    }

    private fun goToResumeTripActivity(trip: TripModel) {
        ResumeTripRouter().launch(requireActivity(), trip)
        /*val intent = Intent(activity, ResumeTripActivity::class.java).apply {
            putExtra("trip", tripModel)
        }
        it.startActivity(intent)
        it.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)*/

    }

    private fun goToEditTripActivity(trip: TripModel) {
        EditTripRouter().launch(requireActivity(), trip, null)

        /*val intent = Intent(activity, EditTripActivity::class.java).apply {
            putExtra("trip", trip)
        }
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)*/
    }

    /*private fun moveHand(){
        val anim = ObjectAnimator.ofFloat(binding.IVHandEmpty, "translationY", 0f, 50f)
        anim.duration = 1000
        anim.repeatCount = Animation.INFINITE;
        anim.repeatMode = ValueAnimator.REVERSE;

        anim.start()
    }*/
}