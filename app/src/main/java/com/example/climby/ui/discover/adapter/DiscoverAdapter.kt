package com.example.climby.ui.discover.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ItemDiscoverBinding
import com.example.climby.ui.profile.RequestsActivity
import com.example.climby.utils.Commons
import com.example.climby.utils.ReservationStatus


class DiscoverAdapter(tripData: List<TripModel>, context: Context) : RecyclerView.Adapter<DiscoverAdapter.DataViewHolder>() {

    private var tripsList: List<TripModel> = ArrayList()
    private var context: Context
    private var userSession: UserModel = Commons.userSession!!
    private lateinit var userDiscoverAdapter: UserDiscoverAdapter
    private lateinit var mlistener: OnItemClickListener

    init {
        this.tripsList = tripData
        this.context = context
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onClickAddMe(position: Int)
        fun onClickRemoveMe(_it: BookingModel, position: Int)
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

        private val binding = ItemDiscoverBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(trip: TripModel) = with(binding) {
            var accepted = 0
            val acceptedBookingList: MutableList<BookingModel> = arrayListOf()
            TVType.text = trip.type?.name + " en"
            TVPlaceDate.text = trip.site?.name + ", \n" + (trip.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip.departure?.let { Commons.getDate(it) }
            trip.type?.name?.let { setPhotoTrip(it, IVPlace,  context) }
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(trip.driver?.photo).into(CVDriver)
            Commons.setTextButton(BTRequest, trip)

            if (trip.driver?.id == userSession.id) { //El viaje pertenece al usuario logueado
                trip.bookings?.forEach { _it ->
                    if (_it.status == ReservationStatus.ACCEPTED.status || _it.status ==ReservationStatus.UNANSWERED.status ) {
                        acceptedBookingList.add(_it)
                        accepted ++
                    }
                }
                if (trip.bookings?.isEmpty() == true) { // El viaje no tiene reservas
                    BTRequest.text = "Aún no tienes peticiones"
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.disable)
                    false.also { BTRequest.isEnabled = it }

                } else { // El viaje tiene reservas
                    BTRequest.text = "Ver peticiones"
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.primary)
                    BTRequest.isEnabled = true
                    BTRequest.setOnClickListener {
                        val intent = Intent(context, RequestsActivity::class.java).apply {
                            putExtra("trip", trip)
                        }
                        context.startActivities(arrayOf(intent))
                    }
                }
                if (accepted > 0)
                    TVUsers.text = "Tú y $accepted más "
                else
                    TVUsers.text = "Tú"
            } else { //El viaje no pertenece al usuario logueado
                var userPassager = false
                var userPassagerAccepted = false

                trip.bookings?.forEach { _it ->
                    if (_it.passenger?.id ?: 0 == userSession.id) { //El usuario está como pasajero en este viaje
                        userPassager = true
                        when (_it.status) {
                            ReservationStatus.ACCEPTED.status -> { // Ha sido aceptado
                                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
                                BTRequest.text = "Te has unido\r\nLiberar plaza"
                                BTRequest.isEnabled = true
                                BTRequest.setOnClickListener {
                                    mlistener.onClickRemoveMe(_it, adapterPosition)

                                }
                                acceptedBookingList.add(_it)
                                accepted++
                                userPassagerAccepted = true
                            }
                            ReservationStatus.UNANSWERED.status -> { // Esta esperando respuesta
                                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
                                BTRequest.text = "Solicitado"
                                BTRequest.isEnabled = true
                                BTRequest.setOnClickListener {
                                    mlistener.onClickRemoveMe(_it, adapterPosition)
                                    trip.bookings!!.remove(_it)
                                }
                            }
                            ReservationStatus.REFUSE.status -> { // Ha sido rechazado
                                BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.red_light)
                                BTRequest.text = "Rechazado"
                                BTRequest.isEnabled = false
                            }
                        }
                    } else { //El usuario no está como pasajero en este viaje
                        if (_it.status == ReservationStatus.ACCEPTED.status) {
                            accepted++
                        }

                        acceptedBookingList.add(_it)
                    }
                }
                if(!userPassager){
                    BTRequest.text = "Pedir unirme\r\n" + trip.availablePlaces + " plazas"
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.primary)
                    BTRequest.setOnClickListener {
                        mlistener.onClickAddMe(adapterPosition)
                        BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
                        BTRequest.text = "Solicitado"
                    }
                }
                if (accepted == trip.availablePlaces && !userPassagerAccepted) {
                    BTRequest.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.disable)
                    BTRequest.text = "Completo"
                    BTRequest.isEnabled = false
                }
                if (accepted > 0)
                    TVUsers.text = (trip.driver?.name?.split(" ")?.get(0) ?: "") + " y " + accepted + " más "
                else
                    TVUsers.text = trip.driver?.name?.split(" ")?.get(0) ?: ""
            }

            RVUsers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

            if (!acceptedBookingList.isNullOrEmpty()) {
                userDiscoverAdapter = UserDiscoverAdapter(acceptedBookingList, context)
                RVUsers.adapter = userDiscoverAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_discover, parent, false), mlistener
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tripsList[position])
    }

    override fun getItemCount(): Int = tripsList.size

    private fun setPhotoTrip(type: String, ivPlace: ImageView, context: Context) {
        when (type) {
            "Boulder" -> {
                Glide.with(context).load(R.mipmap.boulder).error(R.mipmap.default_picture).into(ivPlace)
            }
            "Deportiva" -> {
                Glide.with(context).load(R.mipmap.lead).error(R.mipmap.default_picture).into(ivPlace)
            }
            "Rocódromo" -> {
                Glide.with(context).load(R.mipmap.gym).error(R.mipmap.default_picture).into(ivPlace)
            }
            "Clásica" -> {
                Glide.with(context).load(R.mipmap.trad).error(R.mipmap.default_picture).into(ivPlace)
            }
            else -> {
                Glide.with(context).load(R.mipmap.default_picture).error(R.mipmap.default_picture).into(ivPlace)
            }
        }
    }
}
