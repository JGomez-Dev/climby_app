package com.example.climby.ui.discover

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.FragmentDiscoverBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.discover.viewmodel.DiscoverViewModel
import com.example.climby.utils.Commons
import com.example.climby.view.activity.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var province: String = "Seleccione..."

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        discoverViewModel = ViewModelProvider(this)[DiscoverViewModel::class.java]
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        val view: View = binding.root

        val navBar: BottomNavigationView = activity?.findViewById(R.id.nav_view)!!
        navBar.isVisible = true

        if(!Commons.isInternetAvailable(requireContext().applicationContext)){
            binding.CLNotConnection.isVisible = true
            animateConnection()
            binding.TVRetryConexion.setOnClickListener {
                reloadFragment()
            }
        }
        else {
            getData()
            binding.CLNotConnection.isVisible = false
            binding.RVTrips.layoutManager = LinearLayoutManager(activity)
            discoverViewModel.tripsModel.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    binding.CLTripsEmpty.isVisible = true
                    binding.RVTrips.isVisible = false
                } else {
                    binding.CLTripsEmpty.isVisible = false
                    binding.RVTrips.isVisible = true
                    /*val acceptedTripList: MutableList<TripModel> = arrayListOf()
                it.forEach { _it ->
                    if(_it.bookings?.isNullOrEmpty() != true){
                        _it.bookings!!.forEach { b_it->
                            if(b_it.status != ReservationStatus.REFUSE.status){
                                acceptedTripList.add(_it)
                            }
                        }
                    }
                }*/
                    discoverAdapter = DiscoverAdapter(it, requireContext())
                    binding.RVTrips.adapter = discoverAdapter
                    discoverAdapter.setOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            loadTripUsers(it[position])
                        }

                        override fun onClickAddMe(position: Int) {
                            saveBooking(it, position)
                        }

                        override fun onClickRemoveMe(_it: BookingModel, position: Int) {
                            showDialog(view, _it, it, position)
                        }

                        override fun onItemShowResume(position: Int) {

                        }
                    })
                }
            }

            binding.LYIDiscoverOutputs.setOnClickListener {
                loadProvinces()
            }

            discoverViewModel.isBadResponse.observe(viewLifecycleOwner) {
                binding.CLBadConnection.isVisible = it
                binding.CLTripsEmpty.isVisible = !it
            }

            binding.TVRetry.setOnClickListener {
                discoverViewModel.getTrips(requireContext().applicationContext, province)
            }

            discoverViewModel.isLoading.observe(viewLifecycleOwner) {
                binding.PBDiscover.isVisible = it
            }

            binding.TBSeach.addOnButtonCheckedListener { _, checkedId, isChecked ->
                getFilterAndSendQuery(isChecked, checkedId)

            }

            animateHand()
        }
        return view
    }

    private fun reloadFragment() {
        activity?.let {
            val intent = Intent(requireContext().applicationContext, MainActivity::class.java)
            it.startActivity(intent)
            it.overridePendingTransition(0, 0)
            it.finish()
        }
    }

    private fun animateConnection() {
        val anim = ObjectAnimator.ofFloat(binding.TVDontService, "translationY", 50f, 0f)
        anim.duration = 1000
        anim.repeatCount = Animation.ABSOLUTE

        anim.start()
    }


    private fun animateHand() {
        val anim = ObjectAnimator.ofFloat(binding.IVHandEmpty, "translationY", 0f, 50f)
        anim.duration = 1000
        anim.repeatCount = Animation.INFINITE
        anim.repeatMode = ValueAnimator.REVERSE

        anim.start()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDialog(view: View, booking: BookingModel, it: List<TripModel>, position: Int) {
        AlertDialog.Builder(view.context)
            .setTitle("Eliminar solicitud")
            .setMessage("Dejarás libre tu plaza para que otra persona pueda ocuparla")
            .setNegativeButton(R.string.cancel) { View, _ ->
                View.dismiss()
            }
            .setPositiveButton("Aceptar") { View, _ ->
                deleteBooking(booking)
                it[position].bookings?.remove(booking)
                discoverAdapter.notifyDataSetChanged()
                Commons.sendNotification(it[position].driver?.token!!,
                    booking.passenger?.name!!.split(" ")[0] + " ha cancelado su asistencia",
                    "AuthActivity",
                    it[position].id.toString(),
                    "RequestsActivity",
                    booking.passenger.name.split(" ")[0] + " ha cancelado su asistencia a la salida a " + it[position].site?.name + " el " + it[position].departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(it[position].departure.toString() + "."),
                    requireContext(),
                    requireActivity()
                )
                View.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }

    private fun deleteBooking(bookingModel: BookingModel) {
        discoverViewModel.deleteBooking(bookingModel)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun saveBooking(it: List<TripModel>, position: Int) {
        Commons.sendNotification(
            it[position].driver?.token!!,
            "Tienes una solicitud pendiente",
            "AuthActivity",
            it[position].id.toString(),
            "RequestsActivity",
            Commons.userSession?.name.toString().split(" ")[0] + " ha pedido unirse a tu salida a " + it[position].site?.name + " el " + it[position].departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(it[position].departure.toString()),
            requireContext(),
            requireActivity()
        )
        val bookingModel = BookingModel(0, Commons.userSession, it[position].id, status = false, valuationStatus = false, date = now(), null)
        discoverViewModel.saveBooking(bookingModel)
        it[position].bookings?.add(bookingModel)
        discoverAdapter.notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    private fun now(): String {
        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()).toString()
    }

    /*private fun getPositionItem(spinner: Spinner, province: String?): Int {
        var position = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().split(" ")[0].equals(province, ignoreCase = true)) {
                position = i
            }
        }
        return position
    }*/

    private fun getData() {
        val bundle = activity?.intent?.extras
        if (bundle != null) {
            province = bundle.getString("province").toString()
            if (province != "null") {
                binding.TVCommunity.text = province
                discoverViewModel.getTrips(requireContext().applicationContext, province)
            } else {
                getLocation()
            }
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireContext().applicationContext, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext().applicationContext, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_COARSE_LOCATION), 1000)
        }
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        latitude = location?.latitude!!
                        longitude = location.longitude
                        province = getProvinceByLatLong(location)
                        binding.TVCommunity.text = province
                        discoverViewModel.getTrips(requireContext().applicationContext, province)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Sin localización / Lo tenemos que ver", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No es posible saber su ubicacion", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getProvinceByLatLong(location: Location): String {
        val geocode = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocode.getFromLocation(location.latitude, location.longitude, 1)

        /*val direction = addresses[0].getAddressLine(0)
        val city = addresses[0].locality*/
        /*val province = addresses[0].subAdminArea*/
        /*val community = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode
        val knownName = addresses[0].featureName*/
        return addresses[0].subAdminArea
    }

    private fun loadProvinces() {
        val intent = Intent(activity, ProvinceActivity::class.java).apply {
            putExtra("province", binding.TVCommunity.text)
        }

        startActivity(intent)

        activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)

    }

    private fun loadTripUsers(trip: TripModel) {
        val intent = Intent(activity, TripUsersActivity::class.java).apply {
            putExtra("trip", trip)
            putExtra("from", "discover")
        }
        startActivity(intent)
    }

    private fun getFilterAndSendQuery(isChecked: Boolean, checkedId: Int) {
        if (isChecked) {
            when (checkedId) {
                R.id.BTAll -> {
                    discoverViewModel.getTrips(requireContext().applicationContext, province)
                }
                R.id.BTNextWeekend -> discoverViewModel.getTripsType("NextWeekend", province.split(" ")[0])
                R.id.BTBoulder -> discoverViewModel.getTripsType("Boulder", province.split(" ")[0])
                R.id.BTLead -> discoverViewModel.getTripsType("Deportiva", province.split(" ")[0])
                R.id.BTRocodromo -> discoverViewModel.getTripsType("Rocódromo", province.split(" ")[0])
                R.id.BTClassic -> discoverViewModel.getTripsType("Clásica", province.split(" ")[0])
            }
        }
    }
}