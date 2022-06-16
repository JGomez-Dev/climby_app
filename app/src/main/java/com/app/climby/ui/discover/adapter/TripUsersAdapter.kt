package com.app.climby.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.user.UserModel
import com.app.climby.ui.profile.adapter.RequestAdapter
import de.hdodenhof.circleimageview.CircleImageView

class TripUsersAdapter(bookingsList: List<BookingModel>, context: Context) : RecyclerView.Adapter<TripUsersAdapter.DataViewHolder>() {

    private var bookingsList: List<BookingModel> = ArrayList()
    private var context: Context
    private lateinit var mListener: OnClickListener


    init {
        this.bookingsList = bookingsList
        this.context = context
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_trip_users, parent, false), mListener
    )

    override fun onBindViewHolder(holder: TripUsersAdapter.DataViewHolder, position: Int) {
        holder.bind(bookingsList[position])
    }

    override fun getItemCount(): Int = bookingsList.size

    inner class DataViewHolder(itemView: View, listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val ciVPassengers: CircleImageView = itemView.findViewById(R.id.CIVPassengers)
        private val tVNamePassengers: TextView = itemView.findViewById(R.id.TVNamePassengers)
        private val tVExperience: TextView = itemView.findViewById(R.id.TVExperience)
        private val tVExitsAdmin: TextView = itemView.findViewById(R.id.TVExitsPassengers)

        private val iVStart1Passengers: ImageView = itemView.findViewById(R.id.IVStart1Passengers)
        private val iVStart2Passengers: ImageView = itemView.findViewById(R.id.IVStart2Passengers)
        private val iVStart3Passengers: ImageView = itemView.findViewById(R.id.IVStart3Passengers)


        fun bind(result: BookingModel) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(result.passenger?.photo).into(ciVPassengers)
            tVNamePassengers.text = result.passenger?.name?.split(" ")?.get(0) ?: ""
            tVExperience.text = result.passenger?.experience
            tVExitsAdmin.text = if (result.passenger?.outings.toString() == "1") {
                result.passenger?.outings.toString() + " salida"
            } else {
                result.passenger?.outings.toString() + " salidas"
            }
            result.passenger?.let { setStart(it) }
        }

        private fun setStart(passenger: UserModel) {
            when {
                passenger.score / passenger.ratings > 2.75 -> {
                    iVStart1Passengers.setImageResource(R.mipmap.star)
                    iVStart2Passengers.setImageResource(R.mipmap.star)
                    iVStart3Passengers.setImageResource(R.mipmap.star)
                }
                passenger.score / passenger.ratings  in 2.25..2.75 -> {
                    iVStart1Passengers.setImageResource(R.mipmap.star)
                    iVStart2Passengers.setImageResource(R.mipmap.star)
                    iVStart3Passengers.setImageResource(R.mipmap.medstart)
                }
                passenger.score / passenger.ratings  in 1.75..2.25 -> {
                    iVStart1Passengers.setImageResource(R.mipmap.star)
                    iVStart2Passengers.setImageResource(R.mipmap.star)
                    iVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score / passenger.ratings  in 1.25..1.75 -> {
                    iVStart1Passengers.setImageResource(R.mipmap.star)
                    iVStart2Passengers.setImageResource(R.mipmap.medstart)
                    iVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score / passenger.ratings  in 0.75..1.25 -> {
                    iVStart1Passengers.setImageResource(R.mipmap.star)
                    iVStart2Passengers.setImageResource(R.mipmap.withoutstart)
                    iVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score / passenger.ratings <= 0.75 -> {
                    iVStart1Passengers.setImageResource(R.mipmap.medstart)
                    iVStart2Passengers.setImageResource(R.mipmap.withoutstart)
                    iVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
            }
        }
    }
}