package com.example.climby.ui.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.ui.discover.adapter.UserDiscoverAdapter
import com.example.climby.ui.profile.EditProfileActivity
import com.example.climby.ui.profile.EditTripActivity
import com.example.climby.ui.profile.RequestsActivity
import com.example.climby.utils.Commons
import com.example.climby.utils.ReservationStatus
import de.hdodenhof.circleimageview.CircleImageView


class DiscoverAdapterProfile(tripData: List<TripModel>, context: Context) : RecyclerView.Adapter<DiscoverAdapterProfile.DataViewHolder>() {


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
    }

    fun SetOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }

    inner class DataViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val tvType: TextView = itemView.findViewById(R.id.TVType)
        private val tvPlaceDate: TextView = itemView.findViewById(R.id.TVPlaceDate)
        private val ivPlace: ImageView = itemView.findViewById(R.id.IVPlace)
        private val cvDriver: CircleImageView = itemView.findViewById(R.id.CVDriver)
        private val btRequest: Button = itemView.findViewById(R.id.BTRequest)
        private val tVUsers: TextView = itemView.findViewById(R.id.TVUsers)
        private val rvUserv: RecyclerView = itemView.findViewById(R.id.RVUsers)
        private val tVEdit: TextView = itemView.findViewById(R.id.TVEdit)

        @SuppressLint("SetTextI18n")
        fun bind(trip: TripModel) {
            var accepted = 0
            var request = 0
            val acceptedBookingList: MutableList<BookingModel> = arrayListOf()

            tvType.text = trip.type?.name + " en"
            tvPlaceDate.text = trip.site?.name + ", " + (trip.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + " " + trip.departure?.let { Commons.getDate(it) }
            trip.type?.name?.let { setPhotoTrip(it, ivPlace, context) }
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_person_24).error(R.drawable.ic_baseline_person_24)).load(trip.driver?.photo).into(cvDriver)
            Commons.setTextButton(btRequest, trip)
            var contRefuse = 0
            trip.bookings?.forEach { it ->
                if(it.status == ReservationStatus.REFUSE.status){
                    contRefuse ++
                }
            }
            if (trip.bookings?.isEmpty() == true || contRefuse == trip.bookings?.size) {
                btRequest.text = "Aún no tienes peticiones"
                btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.disable);
                btRequest.isEnabled = false

            } else {

                btRequest.text = "Ver peticiones"
                /*comprobarEstadoReservas(holder, nuevoViaje, reservaList, reservaListFiltradas)*/
                trip.bookings?.forEach {
                    if (it.passenger?.id ?: 0 == userSession.id) {
                        when (it.status) {
                            ReservationStatus.ACCEPTED.status -> {
                                btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                                btRequest.text = "Te has unido\r\nLiberar plaza"
                                acceptedBookingList.add(it)
                            }
                            ReservationStatus.UNANSWERED.status -> {
                                btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                                btRequest.text = "Solicitado"
                            }
                            ReservationStatus.REFUSE.status -> {
                                btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.red_light);
                                btRequest.text = "Rechazado"
                                btRequest.isEnabled = false
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
                                /*accepted++*/
                                request++
                                acceptedBookingList.add(it)
                            }
                            /*ReservationStatus.REFUSE.status -> {
                                *//*accepted++*//*
                                *//*request++*//*
                                acceptedBookingList.add(it)
                            }*/
                        }
                    }
                }

                if (accepted == trip.availablePlaces/* && userAccepted*/) {
                    btRequest.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
                    btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                    btRequest.text = "Completo"
                    /*btRequest.isEnabled = false*/
                }
            }
            if (accepted > 0)
                tVUsers.text = "Tú y $request más "
            else
                tVUsers.text = "Tú"

            rvUserv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            tVEdit.setOnClickListener {
                val intent =Intent(context, EditTripActivity::class.java).apply {
                    putExtra("trip", trip)
                }
                context.startActivities(arrayOf(intent))

            }
            btRequest.setOnClickListener {
                val intent =Intent(context, RequestsActivity::class.java).apply {
                    putExtra("trip", trip)
                }
                context.startActivities(arrayOf(intent))
            }
            if (!acceptedBookingList.isNullOrEmpty()) {
                userDiscoverAdapter = trip.bookings?.let { UserDiscoverAdapter(acceptedBookingList, context) }!!
                rvUserv.adapter = userDiscoverAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_discover_profile, parent, false), mlistener
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tripsList[position])
    }

    override fun getItemCount(): Int = tripsList.size

    fun setPhotoTrip(type: String, ivPlace: ImageView, context: Context) {
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
