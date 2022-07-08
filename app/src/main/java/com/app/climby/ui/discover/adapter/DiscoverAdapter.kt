package com.app.climby.ui.discover.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.ItemDiscoverBinding
import com.app.climby.ui.profile.router.RequestsRouter
import com.app.climby.util.Commons
import com.app.climby.util.From
import com.app.climby.util.ReservationStatus
import com.app.climby.util.UIUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*


class DiscoverAdapter(tripData: List<TripModel>, context: Context, from: From, fragmentActivity: FragmentActivity) : RecyclerView.Adapter<DiscoverAdapter.DataViewHolder>() {

    private var tripsList: List<TripModel> = ArrayList()
    private var context: Context
    private lateinit var userDiscoverAdapter: UserDiscoverAdapter
    private lateinit var mlistener: OnItemClickListener
    private var from: From
    private var fragmentActivity: FragmentActivity

    init {
        this.tripsList = tripData
        this.context = context
        this.from = from
        this.fragmentActivity = fragmentActivity
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onClickAddMe(position: Int)
        fun onClickRemoveMe(_it: BookingModel, position: Int)
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

        private val binding = ItemDiscoverBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(trip: TripModel) = with(binding) {
            var accepted = 0
            val acceptedBookingList: MutableList<BookingModel> = arrayListOf()
            TVType.text = trip.type?.name + " en"
            TVPlaceDate.text = trip.site?.name + ", " + (trip.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip.departure?.let { Commons.getDate(it) }
            trip.type?.name?.let { UIUtil.setPhotoTrip(it, context, IVPlace) }
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(trip.driver?.photo).into(CVDriver)
            Commons.setTextButton(BTRequest, trip)
            var contRefuse = 0
            trip.bookings?.forEach { it ->
                if (it.status == ReservationStatus.REFUSE.status) {
                    contRefuse++
                }
            }
            if (trip.driver?.id == Commons.userSession?.id) { //El viaje pertenece al usuario logueado
                trip.bookings?.forEach { _it ->
                    if (_it.status == ReservationStatus.ACCEPTED.status || _it.status ==ReservationStatus.UNANSWERED.status ) {
                        acceptedBookingList.add(_it)
                        accepted ++
                    }
                }
                if (trip.bookings?.isEmpty() == true || contRefuse == trip.bookings?.size) { // El viaje no tiene reservas
                    BTRequest.text = "Aún no tienes peticiones"
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.disable)
                    false.also { BTRequest.isEnabled = it }

                } else { // El viaje tiene reservas
                    BTRequest.text = "Ver peticiones"
                    BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
                    BTRequest.isEnabled = true

                    var unreadMessages = 0
                    trip.bookings?.forEach { it ->
                        if (it.status == false) {
                            unreadMessages++
                        }
                    }

                    if(unreadMessages != 0){
                        binding.TVNumberMessage.isVisible = true
                        binding.CVNumberMessage.isVisible = true
                        binding.TVNumberMessage.text = unreadMessages.toString()
                    }else{
                        binding.TVNumberMessage.isVisible = false
                        binding.CVNumberMessage.isVisible = false
                    }

                    binding.TVNumberMessage.text = unreadMessages.toString()
                    BTRequest.setOnClickListener {
                        RequestsRouter().launch(fragmentActivity, trip, from)
                    }
                }
                if(accepted < 3)
                    TVUsers.isVisible = false
                else{
                    TVUsers.isVisible = true
                    TVUsers.text = "+ " + (accepted-2)
                }
            } else {
                var userPassager = false
                var userPassagerAccepted = false

                trip.bookings?.forEach { _it ->
                    if (_it.passenger?.id ?: 0 == Commons.userSession?.id) { //El usuario está como pasajero en este viaje
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
                    } else {
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

                val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
                val departure =  trip.departure.toString().split(" ")[0]
                if (departure < currentDate) {
                    if (trip.bookings.isNullOrEmpty()) {
                        BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                        BTRequest.text = "Terminado\r\nSin peticiones"
                        BTRequest.isEnabled = false
                    } else {

                        var unreadMessages = 0
                        trip.bookings?.forEach { it ->
                            if (it.message?.read == false) {
                                unreadMessages++
                            }
                        }

                        if(unreadMessages != 0){
                            TVNumberMessage.isVisible = true
                            CVNumberMessage.isVisible = true
                            TVNumberMessage.text = unreadMessages.toString()
                        }else{
                            TVNumberMessage.isVisible = false
                            CVNumberMessage.isVisible = false
                        }

                        BTRequest.setOnClickListener {
                            mlistener.onItemShowResume(adapterPosition)
                        }

                        BTRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                        BTRequest.text = "Terminado\r\nVer resumen"
                    }
                }
                if(accepted < 3)
                    TVUsers.isVisible = false
                else{
                    TVUsers.isVisible = true
                    TVUsers.text = "+ " + (accepted-2)
                }

                /*if (accepted > 3)
                    TVUsers.text = "+ $accepted"
                else
                    TVUsers.text = trip.driver?.name?.split(" ")?.get(0) ?: ""*/
            }

            RVUsers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

            if (!acceptedBookingList.isNullOrEmpty()) {
                userDiscoverAdapter = UserDiscoverAdapter(acceptedBookingList.take(2), context)
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

}
