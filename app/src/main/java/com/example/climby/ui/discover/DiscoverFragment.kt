package com.example.climby.ui.discover

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.FragmentDiscoverBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.DiscoverViewModel
import com.example.climby.ui.publish.viewmodel.PublishViewModel
import com.example.climby.utils.Commons
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter
    private var selectedProvince: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        val view: View = binding.root


        binding.RVTrips.layoutManager = LinearLayoutManager(activity)
        discoverViewModel.tripsModel.observe(viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty()){
                binding.CLTripsEmpty.isVisible = true
                binding.RVTrips.isVisible = false
            }else{
                binding.CLTripsEmpty.isVisible = false
                binding.RVTrips.isVisible = true
                discoverAdapter = DiscoverAdapter(it, requireContext())
                binding.RVTrips.adapter = discoverAdapter
                discoverAdapter.SetOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        loadActivity(it[position])
                    }
                })
            }

        })

        discoverViewModel.provincesModel.observe(viewLifecycleOwner, Observer {
            val arrayAdapter = ArrayAdapter(requireContext().applicationContext, R.layout.spinner_province_row, R.id.TVMadrid, it)
            arrayAdapter.setDropDownViewResource(R.layout.color_spinner)
            binding.SPCommunity.adapter = arrayAdapter
            binding.SPCommunity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedProvince = parent!!.getItemAtPosition(position) as String
                }
            }
        })

        discoverViewModel.getProvince()
        discoverViewModel.getTrips()
        discoverViewModel.isBadResponse.observe(viewLifecycleOwner, Observer {
            binding.CLBadConnection.isVisible = it
            binding.CLTripsEmpty.isVisible = !it

        })
        binding.TVRetry.setOnClickListener {
            discoverViewModel.getTrips()
        }
        discoverViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBDiscover.isVisible = it
        })
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            getFilterAndSendQuery(isChecked, checkedId, selectedProvince)
        }
        return view
    }

    fun loadActivity(trip: TripModel) {
        val intent = Intent(activity, TripUsersActivity::class.java).apply {
            putExtra("trip", trip)
        }
        startActivity(intent)
    }


    private fun getFilterAndSendQuery(isChecked: Boolean, checkedId: Int, selectedProvince: String) {
        if (isChecked) {
            when (checkedId) {
                R.id.BTAll -> discoverViewModel.getTrips()
                R.id.BTNextWeekend -> discoverViewModel.getTripsType("NextWeekend",selectedProvince.split(" ")[0])
                R.id.BTBoulder -> discoverViewModel.getTripsType("Boulder", selectedProvince.split(" ")[0])
                R.id.BTLead -> discoverViewModel.getTripsType("Deportiva", selectedProvince.split(" ")[0])
                R.id.BTRocodromo -> discoverViewModel.getTripsType("Rocódromo", selectedProvince.split(" ")[0])
                R.id.BTClassic -> discoverViewModel.getTripsType("Clásica", selectedProvince.split(" ")[0])
            }
        }
    }
}