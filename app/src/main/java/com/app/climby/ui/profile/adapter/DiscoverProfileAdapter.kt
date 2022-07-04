package com.app.climby.ui.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ItemDiscoverProfileBinding
import com.app.climby.ui.discover.adapter.UserDiscoverAdapter
import com.app.climby.ui.profile.router.RequestsRouter
import com.app.climby.util.Commons
import com.app.climby.util.From
import com.app.climby.util.ReservationStatus
import com.app.climby.util.UIUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class DiscoverProfileAdapter(tripData: List<TripModel>, context: Context, fragmentActivity: FragmentActivity, from: From) : RecyclerView.Adapter<DiscoverProfileAdapter.DataViewHolder>() {

    private var tripsList: List<TripModel> = ArrayList()

    private var context: Context
    private var userSession: UserModel = Commons.userSession!!
    private lateinit var userDiscoverAdapter: UserDiscoverAdapter
    private lateinit var mlistener: OnItemClickListener
    private var fragmentActivity: FragmentActivity
    private var from: From

    init {
        this.tripsList = tripData
        this.context = context
        this.fragmentActivity = fragmentActivity
        this.from = from
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemEdit(position: Int)
        fun onItemShowResume(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }

    inner class DataViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val binding = ItemDiscoverProfileBinding.bind(itemView)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(trip: TripModel)  = with(binding){
            var accepted = 0
            var request = 0
            val acceptedBookingList: MutableList<BookingModel> = arrayListOf()

            TVType.text = trip.type?.name + " en"
            TVPlaceDate.text = trip.site?.name + ", \n" + (trip.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip.departure?.let { Commons.getDate(it) }
            trip.type?.name?.let { UIUtil.setPhotoTrip(it, context, IVPlace)  }
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(trip.driver?.photo).into(CVDriver)
            Commons.setTextButton(BTRequest, trip)
            val reservationRefuse: Int = reservationRefuse(trip)
            messageBubble(trip)
            if (trip.bookings?.isEmpty() == true || reservationRefuse == trip.bookings?.size) {
                BTRequest.text = "Aún no tienes peticiones"
                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.disable);
                BTRequest.isEnabled = false

            } else {
                BTRequest.text = "Ver peticiones"
                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)

                BTRequest.setOnClickListener {
                    RequestsRouter().launch(fragmentActivity, trip, from)
                }

                trip.bookings?.forEach {
                    if (it.passenger?.id ?: 0 == userSession.id) {
                        when (it.status) {
                            ReservationStatus.ACCEPTED.status -> {
                                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                                BTRequest.text = "Te has unido\r\nLiberar plaza"
                                acceptedBookingList.add(it)
                            }
                            ReservationStatus.UNANSWERED.status -> {
                                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                                BTRequest.text = "Solicitado"
                            }
                            ReservationStatus.REFUSE.status -> {
                                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.red_light);
                                BTRequest.text = "Rechazado"
                                BTRequest.isEnabled = false
                            }
                        }
                    } else {
                        when (it.status) {
                            ReservationStatus.ACCEPTED.status -> {
                                accepted++
                                request++
                                acceptedBookingList.add(it)
                            }
                            ReservationStatus.UNANSWERED.status -> {
                                request++
                                acceptedBookingList.add(it)
                            }
                        }
                    }
                }

                if (accepted == trip.availablePlaces) {
                    BTRequest.setOnClickListener {
                        RequestsRouter().launch(fragmentActivity, trip, null)
                    }
                    BTRequest.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                    BTRequest.text = "Completo"
                }
            }

            val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
            val departure =  trip.departure.toString().split(" ")[0]
            if (departure < currentDate) {
                TVEdit.isVisible = false
                if (trip.bookings.isNullOrEmpty()) {
                    BTRequest.text = "Terminado\r\nSin peticiones"
                    BTRequest.isEnabled = false
                } else {
                    BTRequest.setOnClickListener {
                        mlistener.onItemShowResume(adapterPosition)
                    }

                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                    BTRequest.text = "Terminado\r\nVer resumen"
                }
            }

            TVUsers.text =   if (accepted > 0) "Tú y $request más " else "Tú"

            RVUsers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            TVEdit.setOnClickListener {
                mlistener.onItemEdit(adapterPosition)
            }

            if (!acceptedBookingList.isNullOrEmpty()) {
                userDiscoverAdapter = trip.bookings?.let { UserDiscoverAdapter(acceptedBookingList, context) }!!
                RVUsers.adapter = userDiscoverAdapter
            }
        }

        private fun messageBubble(trip: TripModel)= with(binding){
            val unreadMessages = unreadMessages(trip)
            if (unreadMessages != 0) {
                CVNumberMessage.isVisible = true
                TVNumberMessage.isVisible = true
                TVNumberMessage.text = unreadMessages.toString()
            } else {
                TVNumberMessage.isVisible = false
                CVNumberMessage.isVisible = false
            }
        }

        private fun unreadMessages(trip: TripModel): Int {
            var unreadMessages = 0
            trip.bookings?.forEach { it ->
                if (it.status == false) {
                    unreadMessages++
                }
            }
            return unreadMessages
        }

        private fun reservationRefuse(trip: TripModel): Int {
            var refuse = 0
            trip.bookings?.forEach { it ->
                if (it.status == ReservationStatus.REFUSE.status) {
                    refuse++
                }
            }
            return refuse
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_discover_profile, parent, false), mlistener
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tripsList[position])
    }

    override fun getItemCount(): Int = tripsList.size
}
