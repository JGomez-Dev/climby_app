package com.app.climby.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ItemTripUsersBinding

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

    inner class DataViewHolder(itemView: View, listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val binding = ItemTripUsersBinding.bind(itemView)

        fun bind(result: BookingModel) = with(binding) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(result.passenger?.photo).into(CIVPassengers)
            TVNamePassengers.text = result.passenger?.name?.split(" ")?.get(0) ?: ""
            TVExperience.text = result.passenger?.experience
            TVExitsPassengers.text = if (result.passenger?.outings.toString() == "1") {
                result.passenger?.outings.toString() + " salida"
            } else {
                result.passenger?.outings.toString() + " salidas"
            }
            result.passenger?.let { setStart(it) }
        }

        private fun setStart(passenger: UserModel) = with(binding) {
            when {
                passenger.score / passenger.ratings > 2.75 -> {
                    IVStart1Passengers.setImageResource(R.mipmap.star)
                    IVStart2Passengers.setImageResource(R.mipmap.star)
                    IVStart3Passengers.setImageResource(R.mipmap.star)
                }
                passenger.score / passenger.ratings  in 2.25..2.75 -> {
                    IVStart1Passengers.setImageResource(R.mipmap.star)
                    IVStart2Passengers.setImageResource(R.mipmap.star)
                    IVStart3Passengers.setImageResource(R.mipmap.medstart)
                }
                passenger.score / passenger.ratings  in 1.75..2.25 -> {
                    IVStart1Passengers.setImageResource(R.mipmap.star)
                    IVStart2Passengers.setImageResource(R.mipmap.star)
                    IVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score / passenger.ratings  in 1.25..1.75 -> {
                    IVStart1Passengers.setImageResource(R.mipmap.star)
                    IVStart2Passengers.setImageResource(R.mipmap.medstart)
                    IVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score / passenger.ratings  in 0.75..1.25 -> {
                    IVStart1Passengers.setImageResource(R.mipmap.star)
                    IVStart2Passengers.setImageResource(R.mipmap.withoutstart)
                    IVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score / passenger.ratings <= 0.75 -> {
                    IVStart1Passengers.setImageResource(R.mipmap.medstart)
                    IVStart2Passengers.setImageResource(R.mipmap.withoutstart)
                    IVStart3Passengers.setImageResource(R.mipmap.withoutstart)
                }
            }
        }
    }
    override fun getItemCount(): Int = bookingsList.size
}