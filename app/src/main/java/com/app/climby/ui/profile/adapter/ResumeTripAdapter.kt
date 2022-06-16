package com.app.climby.ui.profile.adapter

import android.annotation.SuppressLint
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
import de.hdodenhof.circleimageview.CircleImageView

class ResumeTripAdapter (bookingData: MutableList<BookingModel>, context: Context) : RecyclerView.Adapter<ResumeTripAdapter.DataViewHolder>() {

    private var bookingData:  MutableList<BookingModel> = arrayListOf()
    private var context: Context

    init {
        this.bookingData = bookingData
        this.context = context
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cvItemResumeTrip: CircleImageView = itemView.findViewById(R.id.CVItemResumeTrip)
        private val tvNameItemResumeTrip: TextView = itemView.findViewById(R.id.TVNameItemResumeTrip)
        private val tvMessageTrip: TextView = itemView.findViewById(R.id.TVMessageTrip)

        @SuppressLint("SetTextI18n")
        fun bind(booking: BookingModel) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(booking.passenger?.photo).into(cvItemResumeTrip)
            tvNameItemResumeTrip.text = booking.passenger?.name?.split(" ")!![0]
            tvMessageTrip.text = booking.message?.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_resume_trip, parent, false))

    override fun onBindViewHolder(holder: ResumeTripAdapter.DataViewHolder, position: Int): Unit = holder.bind(bookingData[position])

    override fun getItemCount(): Int = bookingData.size

}