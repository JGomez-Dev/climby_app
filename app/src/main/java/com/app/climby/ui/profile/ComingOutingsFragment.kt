package com.app.climby.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.FragmentComingOutingsBinding
import com.app.climby.ui.discover.adapter.DiscoverAdapter
import com.app.climby.ui.discover.router.TripUsersRouter
import com.app.climby.ui.profile.router.ResumeTripRouter
import com.app.climby.ui.profile.viewmodel.ComingOutingsViewModel
import com.app.climby.util.Commons
import com.app.climby.util.From
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

        if(!Commons.isInternetAvailable(requireContext().applicationContext)){
            binding.CLNotConnection.isVisible = true
        }else {
            binding.CLNotConnection.isVisible = false
            binding.RVTrips.layoutManager = LinearLayoutManager(activity)
            comingOutingsViewModel.tripsModel.observe(viewLifecycleOwner) { tripList ->
                if (tripList.isNullOrEmpty()) {
                    binding.CLTripsEmpty.isVisible = true
                    binding.RVTrips.isVisible = false
                    /*moveHand()*/
                } else {
                    binding.CLTripsEmpty.isVisible = false
                    binding.RVTrips.isVisible = true
                    activity?.let {
                        discoverAdapter = DiscoverAdapter(tripList, requireContext(), From.COMING_OUTINGS, it)
                        binding.RVTrips.adapter = discoverAdapter
                        discoverAdapter.setOnItemClickListener(object : DiscoverAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                loadTripUsers(tripList[position])
                            }

                            override fun onClickAddMe(position: Int) {

                            }

                            override fun onClickRemoveMe(_it: BookingModel, position: Int) {
                                showDialog(view, _it, tripList, position)
                            }

                            override fun onItemShowResume(position: Int) {
                                goToResumeTripActivity(tripList[position])
                            }
                        })
                    }
                }
            }
            comingOutingsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
                binding.PBMyOutings.isVisible = it
            })
            binding.pullToRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.primary))
            binding.pullToRefresh.setOnRefreshListener {
                comingOutingsViewModel.getMyNextOutings()
                binding.pullToRefresh.isRefreshing = false
            }
            comingOutingsViewModel.getMyNextOutings()
        }

        return view
    }

    private fun goToResumeTripActivity(trip: TripModel) {
        ResumeTripRouter().launch(requireActivity(), trip)
        /*activity?.let {
            val intent = Intent(activity, ResumeTripActivity::class.java).apply {
                putExtra("trip", tripModel)
            }
            it.startActivity(intent)
            it.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }*/
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
                    requireContext(),
                    requireActivity()
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
        TripUsersRouter().launch(requireActivity(), trip, From.COMING_OUTINGS)
        /*activity?.let {
            val intent = Intent(activity, TripUsersActivity::class.java).apply {
                putExtra("trip", trip)
            }
            it.startActivity(intent)
            it.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }*/
    }

}