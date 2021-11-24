package com.example.climby.ui.discover.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.utils.Commons
import de.hdodenhof.circleimageview.CircleImageView

enum class ReservationStatus(val status: Boolean?) {
    UNANSWERED(null),
    ACCEPTED(true),
    REFUSE(false),
}

class DiscoverAdapter(tripData: List<TripModel>, context: Context) : RecyclerView.Adapter<DiscoverAdapter.DataViewHolder>() {

    private var tripsList: List<TripModel> = ArrayList()
    private var context: Context
    private var userSession: UserModel = Commons.userSession!!

    init {
        this.tripsList = tripData
        this.context = context
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvType: TextView = itemView.findViewById(R.id.TVType)
        private val tvPlaceDate: TextView = itemView.findViewById(R.id.TVPlaceDate)
        private val ivPlace: ImageView = itemView.findViewById(R.id.IVPlace)
        private val cvDriver: CircleImageView = itemView.findViewById(R.id.CVDriver)
        private val btRequest: Button = itemView.findViewById(R.id.BTRequest)
        private val tVUsers: TextView = itemView.findViewById(R.id.TVUsers)

        @SuppressLint("SetTextI18n")
        fun bind(trip: TripModel) {
            var userAccepted = false
            var accepted = 0
            tvType.text = trip.type + " en"
            tvPlaceDate.text = trip.site.name + ", " + trip.departure.toString().split("-")[2].split(" ")[0] + " " + Commons.getDate(trip.departure.toString()) + "."
            setPhotoTrip(trip.type, ivPlace, context)
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_person_24).error(R.drawable.ic_baseline_person_24)).load(trip.driver.photo).into(cvDriver)
            Commons.setTextButton(btRequest, trip)
            /*result.bookings.forEach {
                if (it.status == ReservationStatus.ACCEPTED.status && it.passenger.id == userSession.id) { // Se debe añadir la condición de que sea el usuario log
                    btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                    btRequest.text = "Solicitado"
                    accepted++
                }
                else if(it.status == ReservationStatus.REFUSE.status) { // Se debe añadir la condición de que sea el usuario log
                    btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                    btRequest.text = "Te has unido"
                    accepted++
                }else if (it.status == ReservationStatus.UNANSWERED.status){
                    btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.browser_actions_bg_grey);
                    btRequest.text = "Rechazado"
                }
            }*/
            if (trip.bookings.isEmpty()) {
                btRequest.text = "Pedir unirme\r\n" + trip.availablePlaces + " plazas"
            } else {
                /*comprobarEstadoReservas(holder, nuevoViaje, reservaList, reservaListFiltradas)*/
                trip.bookings.forEach {
                    if (it.status == ReservationStatus.ACCEPTED.status && it.passenger.id == userSession.id) {
                        if(it.passenger.id == userSession.id){
                            btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.black);
                            btRequest.text = "Te has unido\r\nLiberar plaza"
                            userAccepted = true
                        }else{
                            accepted++
                        }
                    }
                }
                if (accepted == trip.availablePlaces && userAccepted) {
                    btRequest.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
                    btRequest.backgroundTintList = ContextCompat.getColorStateList(context, R.color.grey);
                    btRequest.text = "Completo"
                    btRequest.isEnabled = false
                }
            }
            /*if(accepted > 0)
                tVUsers.text = result.driver.name.split(" ")[0] + " y " + accepted + " más "
            else
                tVUsers.text = result.driver.name.split(" ")[0]*/
        }
    }

    fun setPhotoTrip(type: String, ivPlace: ImageView, context: Context) {
        when (type) {
            "Boulder" -> {
                Glide.with(context)
                    .load(R.mipmap.boulder)
                    .error(R.mipmap.default_picture)
                    .into(ivPlace)
            }
            "Deportiva" -> {
                Glide.with(context)
                    .load(R.mipmap.lead)
                    .error(R.mipmap.default_picture)
                    .into(ivPlace)
            }
            "Rocódromo" -> {
                Glide.with(context)
                    .load(R.mipmap.gym)
                    .error(R.mipmap.default_picture)
                    .into(ivPlace)
            }
            "Clásica" -> {
                Glide.with(context)
                    .load(R.mipmap.trad)
                    .error(R.mipmap.default_picture)
                    .into(ivPlace)
            }
            else -> {
                Glide.with(context)
                    .load(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into(ivPlace)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_discover, parent, false)
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tripsList[position])
    }

    override fun getItemCount(): Int = tripsList.size

}
