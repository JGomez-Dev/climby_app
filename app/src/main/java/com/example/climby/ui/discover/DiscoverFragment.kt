package com.example.climby.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.databinding.FragmentDiscoverBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        val view: View = binding.root


        binding.RVTrips.layoutManager = LinearLayoutManager(activity)
        discoverViewModel.tripsModel.observe(viewLifecycleOwner, Observer {
            discoverAdapter = DiscoverAdapter(it, requireContext())
            binding.RVTrips.adapter = discoverAdapter
            discoverAdapter.SetOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    loadFragment()
                }
            })
        })


        discoverViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBDiscover.isVisible = it
        })
        discoverViewModel.getTrips()
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            getFilterAndSendQuery(isChecked, checkedId)
        }
        return view
    }

    fun loadFragment() {
        val tripUsersFragment = TripUsersFragment()
        val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, tripUsersFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getFilterAndSendQuery(isChecked: Boolean, checkedId: Int) {
        if (isChecked) {
            when (checkedId) {
                R.id.BTAll -> discoverViewModel.getTrips()
                R.id.BTNextWeekend -> discoverViewModel.getTripsType("NextWeekend")
                R.id.BTBoulder -> discoverViewModel.getTripsType("Boulder")
                R.id.BTLead -> discoverViewModel.getTripsType("Deportiva")
                R.id.BTRocodromo -> discoverViewModel.getTripsType("Rocódromo")
                R.id.BTClassic -> discoverViewModel.getTripsType("Clásica")
            }
        }
    }
}