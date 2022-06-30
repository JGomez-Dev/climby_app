package com.app.climby.ui.discover

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ActivityTripUsersBinding
import com.app.climby.ui.discover.adapter.TripUsersAdapter
import com.app.climby.ui.discover.viewmodel.TripUsersViewModel
import com.app.climby.util.Commons
import com.app.climby.util.ReservationStatus
import com.app.climby.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTripUsersBinding
    private lateinit var tripUsersViewModel: TripUsersViewModel
    private lateinit var tripUsersAdapter: TripUsersAdapter
    private var trip: TripModel? = null
    private var userSession: UserModel = Commons.userSession!!

    private var from: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tripUsersViewModel = ViewModelProvider(this)[TripUsersViewModel::class.java]
        binding = ActivityTripUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            onBackPressed()
           /* when (from) {
                "profile" -> {
                    goToMainActivity("profile")
                }
                "discover" -> {
                    onBackPressed()
                }
                else -> {
                    onBackPressed()
                }
            }*/
        }

        tripUsersViewModel.tripModel.observe(this, Observer {
            trip = it
            init()
        })

        binding.pullToRefresh.setColorSchemeColors(ContextCompat.getColor(applicationContext, R.color.primary))
        binding.pullToRefresh.setOnRefreshListener {
            getData()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
    }

    /*private fun goToMainActivity(from: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("from", from)
            putExtra("viewPager", 1)
        }
        startActivity(intent)
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }*/

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.TVVAdmin.text = trip?.driver?.name?.split(" ")?.get(0) ?: ""
        binding.TVExperience.text = trip?.driver?.experience
        binding.TVExitsAdmin.text = if (trip?.driver?.outings.toString() == "1") {
            trip?.driver?.outings.toString() + " salida"
        } else {
            trip?.driver?.outings.toString() + " salidas"
        }
        binding.TVSite.text = trip?.site?.name + ", " + (trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip?.departure?.let { Commons.getDate(it) }
        Glide.with(this).load(trip?.driver?.photo).error(R.mipmap.user).into(binding.CIVAdmin)
        trip?.driver?.let { setStart(it)}
        if(trip?.bookings?.size == 0){
            binding.TVAssistants.visibility = View.GONE
        }else{
            binding.TVAssistants.visibility = View.VISIBLE
        }
        binding.CVAdmin.setOnClickListener {
            Glide.with(applicationContext).load(trip?.driver?.photo).error(R.mipmap.user).into(binding.CVBackgroundRequest)
            binding.FLBackgroundRequest.isVisible = !binding.FLBackgroundRequest.isVisible
        }
        binding.RVAssistants.layoutManager = LinearLayoutManager(this)
        tripUsersAdapter = TripUsersAdapter(acceptedBooking(), this)
        binding.RVAssistants.adapter = tripUsersAdapter
        tripUsersAdapter.setOnClickListener(object : TripUsersAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Glide.with(applicationContext).load(trip?.bookings?.get(position)?.passenger?.photo).error(R.mipmap.user).into(binding.CVBackgroundRequest)
                binding.FLBackgroundRequest.isVisible = !binding.FLBackgroundRequest.isVisible
            }

        })
    }

    private fun acceptedBooking(): MutableList<BookingModel> {
        val acceptedBookingList: MutableList<BookingModel> = arrayListOf()
        trip?.bookings?.forEach { it ->
            if ((trip?.driver?.id ?: 0 == userSession.id) || (it.passenger?.id == userSession.id)) {
                when (it.status) {
                    ReservationStatus.ACCEPTED.status -> {
                        acceptedBookingList.add(it)
                    }
                }
            } else {
                acceptedBookingList.add(it)
            }
        }
        if(acceptedBookingList.isEmpty()){
            binding.TVAssistants.visibility =  View.GONE
        }
        return acceptedBookingList
    }

    private fun getData() {
        val bundle = intent.extras
        if(bundle != null){
            trip = bundle.getParcelable("trip")
            from = bundle.getString("from")
            val idTrip = bundle.getInt("idTrip")
            if (idTrip != 0) {
                tripUsersViewModel.getTripById(idTrip)
            } else {
                init()
            }
        }
    }

    private fun setStart(passenger: UserModel) {
        when {
            passenger.score / passenger.ratings  > 2.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.star)
                binding.IVStart3.setImageResource(R.mipmap.star)
            }
            passenger.score / passenger.ratings  in 2.25..2.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.star)
                binding.IVStart3.setImageResource(R.mipmap.medstart)
            }
            passenger.score / passenger.ratings  in 1.75..2.25 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.star)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
            passenger.score / passenger.ratings  in 1.25..1.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.medstart)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
            passenger.score / passenger.ratings  in 0.75..1.25 -> {
                binding.IVStart1.setImageResource(R.mipmap.star)
                binding.IVStart2.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
            passenger.score / passenger.ratings  <= 0.75 -> {
                binding.IVStart1.setImageResource(R.mipmap.medstart)
                binding.IVStart2.setImageResource(R.mipmap.withoutstart)
                binding.IVStart3.setImageResource(R.mipmap.withoutstart)
            }
        }
    }
}