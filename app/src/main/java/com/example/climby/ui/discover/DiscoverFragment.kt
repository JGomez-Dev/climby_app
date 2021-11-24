package com.example.climby.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.FragmentDiscoverBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.DiscoverViewModel
import com.example.climby.utils.Commons
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var homeViewModel: DiscoverViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        val view: View = binding.root

        binding.RVTrips.layoutManager =  LinearLayoutManager(activity)
        homeViewModel.tripsModel.observe(viewLifecycleOwner, Observer {
            binding.RVTrips.adapter = DiscoverAdapter(it, requireContext())
        })

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBDiscover.isVisible = it
        })
        homeViewModel.getTrips()
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            getFilterAndSendQuery(isChecked, checkedId)
        }
        return view
    }

    private fun getFilterAndSendQuery(isChecked: Boolean, checkedId: Int) {
        if (isChecked) {
            when (checkedId) {
                R.id.BTAll -> homeViewModel.getTrips()
                R.id.BTNextWeekend -> homeViewModel.getTripsType("NextWeekend")
                R.id.BTBoulder -> homeViewModel.getTripsType("Boulder")
                R.id.BTLead -> homeViewModel.getTripsType("Deportiva")
                R.id.BTRocodromo -> homeViewModel.getTripsType("Rocódromo")
                R.id.BTClassic -> homeViewModel.getTripsType("Clásica")
            }
        } /*else {
            when (checkedId) {
                R.id.BTAll -> Toast.makeText(context, "Todas no Check", Toast.LENGTH_LONG).show()
                R.id.BTNextWeekend -> Toast.makeText(context, "Próxima semana no Check", Toast.LENGTH_LONG).show()
                R.id.BTBoulder -> Toast.makeText(context, "Boulder no Check", Toast.LENGTH_LONG).show()
                R.id.BTLead -> Toast.makeText(context, "Cuerda no Check", Toast.LENGTH_LONG).show()
                R.id.BTRocodromo -> Toast.makeText(context, "Rocódromo no Check", Toast.LENGTH_LONG).show()
                R.id.BTClassic -> Toast.makeText(context, "Clásica no Check", Toast.LENGTH_LONG).show()
            }
        }*/
    }
}