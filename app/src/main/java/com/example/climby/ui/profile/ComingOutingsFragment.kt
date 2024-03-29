package com.example.climby.ui.profile

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.FragmentComingOutingsBinding
import com.example.climby.ui.discover.TripUsersActivity
import com.example.climby.ui.discover.adapter.DiscoverAdapter
import com.example.climby.ui.profile.viewmodel.ComingOutingsViewModel
import com.example.climby.utils.Commons
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
                /*moveHand()*/
            }else{
                binding.CLTripsEmpty.isVisible = false
                binding.RVTrips.isVisible = true
                discoverAdapter = DiscoverAdapter(it, requireContext())
                binding.RVTrips.adapter = discoverAdapter
                discoverAdapter.setOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        loadTripUsers(it[position])
                    }

                    override fun onClickAddMe(position: Int) {

                    }

                    override fun onClickRemoveMe(_it: BookingModel, position: Int) {
                        showDialog(view, _it, it, position)
                    }

                    override fun onItemShowResume(position: Int) {
                        showResumeTripActivity(it[position])
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

    private fun showResumeTripActivity(tripModel: TripModel) {
        val intent = Intent(activity, ResumeTripActivity::class.java).apply {
            putExtra("trip", tripModel)
            putExtra("from", "ComingOutings")
        }
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showDialog(view: View, booking: BookingModel, it: List<TripModel>, position: Int) {
        AlertDialog.Builder(view.context)
            .setTitle("Eliminar solicitud")
            .setMessage("Dejarás libre tu plaza para que otra persona pueda ocuparla")
            .setNegativeButton(R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setPositiveButton("Aceptar") { view, _ ->
                deleteBooking(booking, it, position)
                it[position].bookings?.remove(booking)
                discoverAdapter.notifyDataSetChanged()
                Commons.sendNotification(it[position].driver?.token!!,
                    booking.passenger?.name!!.split(" ")[0] + " ha cancelado su asistencia",
                    "AuthActivity",
                    it[position].id.toString(),
                    "RequestsActivity",
                    booking.passenger.name.split(" ")[0] + " ha cancelado su asistencia a la salida a " + it[position].site?.name + " el " + it[position].departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(it[position].departure.toString() + "."),
                    context!!,
                    activity!!
                )
                view.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }

    private fun deleteBooking(bookingModel: BookingModel, it: List<TripModel>, position: Int) {
        comingOutingsViewModel.deleteBooking(bookingModel)
    }

    /*private fun moveHand(){
        val anim = ObjectAnimator.ofFloat(binding.IVHandEmpty, "translationY", 0f, 50f)
        anim.duration = 1000
        anim.repeatCount = Animation.INFINITE;
        anim.repeatMode = ValueAnimator.REVERSE;

        anim.start()
    }*/

    private fun loadTripUsers(trip: TripModel) {
        val intent = Intent(activity, TripUsersActivity::class.java).apply {
            putExtra("trip", trip)
        }
        startActivity(intent)
    }

}