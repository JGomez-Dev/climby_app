package com.example.climby.ui.discover

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var province: String? = "Madrid"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        val view: View = binding.root

        getLocation()

        binding.RVTrips.layoutManager = LinearLayoutManager(activity)
        discoverViewModel.tripsModel.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                binding.CLTripsEmpty.isVisible = true
                binding.RVTrips.isVisible = false
            } else {
                binding.CLTripsEmpty.isVisible = false
                binding.RVTrips.isVisible = true
                discoverAdapter = DiscoverAdapter(it, requireContext())
                binding.RVTrips.adapter = discoverAdapter
                discoverAdapter.setOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        loadTripUsers(it[position])
                    }
                })
            }
        })

        discoverViewModel.provincesModel.observe(viewLifecycleOwner, Observer {
            /*val arrayAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, it) {
                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0)
                        view.setTextColor(ContextCompat.getColor(context, R.color.grey))
                    return view
                }

                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }
            }*/
            val arrayAdapter = ArrayAdapter(requireContext().applicationContext, R.layout.spinner_province_row, R.id.TVMadrid, it)
            arrayAdapter.setDropDownViewResource(R.layout.color_spinner)
            binding.SPCommunity.adapter = arrayAdapter
            binding.SPCommunity.setSelection(getPositionItem(binding.SPCommunity, province))
            binding.SPCommunity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                @RequiresApi(Build.VERSION_CODES.P)
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    province = parent!!.getItemAtPosition(position).toString().split(" ")[0]
                    discoverViewModel.getTrips(requireContext().applicationContext, province!!)
                    binding.TBSeach.check(R.id.BTAll)
                    binding.HSVTButton.scrollTo(0,0)
                }
            }
        })

        discoverViewModel.getProvince()
        discoverViewModel.getTrips(requireContext().applicationContext, province!!)
        discoverViewModel.isBadResponse.observe(viewLifecycleOwner, Observer {
            binding.CLBadConnection.isVisible = it
            binding.CLTripsEmpty.isVisible = !it
        })

        binding.TVRetry.setOnClickListener {
            discoverViewModel.getTrips(requireContext().applicationContext, province!!)
        }

        discoverViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.PBDiscover.isVisible = it
        })

        binding.TBSeach.addOnButtonCheckedListener { _, checkedId, isChecked ->
            getFilterAndSendQuery(isChecked, checkedId)
        }

        return view
    }

    fun getPositionItem(spinner: Spinner, province: String?): Int {
        var position = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().split(" ")[0].equals(province, ignoreCase = true)) {
                position = i
            }
        }
        return position
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext().applicationContext, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext().applicationContext, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_COARSE_LOCATION), 1000)
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                latitude = location?.latitude!!
                longitude = location.longitude
                province = getProvinceByLatLong(location)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Sin localización / Lo tenemos que ver", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getProvinceByLatLong(location: Location): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        val direction = addresses[0].getAddressLine(0)
        val city = addresses[0].locality
        val province = addresses[0].subAdminArea
        val community = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName = addresses[0].featureName
        return province
    }

    private fun loadTripUsers(trip: TripModel) {
        val intent = Intent(activity, TripUsersActivity::class.java).apply {
            putExtra("trip", trip)
        }
        startActivity(intent)
    }

    private fun getFilterAndSendQuery(isChecked: Boolean, checkedId: Int) {
        if (isChecked) {
            when (checkedId) {
                R.id.BTAll -> discoverViewModel.getTrips(requireContext().applicationContext, province!!)
                R.id.BTNextWeekend -> discoverViewModel.getTripsType("NextWeekend", province!!.split(" ")[0])
                R.id.BTBoulder -> discoverViewModel.getTripsType("Boulder", province!!.split(" ")[0])
                R.id.BTLead -> discoverViewModel.getTripsType("Deportiva", province!!.split(" ")[0])
                R.id.BTRocodromo -> discoverViewModel.getTripsType("Rocódromo", province!!.split(" ")[0])
                R.id.BTClassic -> discoverViewModel.getTripsType("Clásica", province!!.split(" ")[0])
            }
        }
    }
}