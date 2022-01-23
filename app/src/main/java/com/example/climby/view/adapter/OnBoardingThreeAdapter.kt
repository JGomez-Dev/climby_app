package com.example.climby.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import de.hdodenhof.circleimageview.CircleImageView


class OnBoardingThreeAdapter(listBookings: List<BookingModel>, context: Context) : RecyclerView.Adapter<OnBoardingThreeAdapter.DataViewHolder>() {

    private var listBookings: List<BookingModel> = ArrayList()
    private var context: Context
    private lateinit var mListener: OnClickListener
    private var oldScore: Double = 0.0
    private var firstTime = true

    init {
        this.listBookings = listBookings
        this.context = context
    }

    interface OnClickListener {
        fun onClickAddStart(position: Int)
        fun onClickRemoveStart(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_qualify_attendees, parent, false)
    )


    override fun onBindViewHolder(holder: OnBoardingThreeAdapter.DataViewHolder, position: Int) {
        holder.bind(listBookings[position])
    }

    override fun getItemCount(): Int = listBookings.size

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cvAttendees: CircleImageView = itemView.findViewById(R.id.CVAttendees)
        private val tvNameQualifyAttendees: TextView = itemView.findViewById(R.id.TVNameQualifyAttendees)
        private val ivAddStart: ImageView = itemView.findViewById(R.id.IVAddStart)
        private val ivRemoveStart: ImageView = itemView.findViewById(R.id.IVRemoveStart)
        private val ivStart1: ImageView = itemView.findViewById(R.id.IVStart1)
        private val ivStart2: ImageView = itemView.findViewById(R.id.IVStart2)
        private val ivStart3: ImageView = itemView.findViewById(R.id.IVStart3)
        private var contStart = 3.0

        fun bind(booking: BookingModel) {
            booking.passenger?.ratings = booking.passenger?.ratings?.plus(1)!!
            oldScore = booking.passenger.score
            booking.passenger.score  = (booking.passenger.score.plus(contStart))
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(booking.passenger.photo).into(cvAttendees)
            tvNameQualifyAttendees.text = booking.passenger.name?.split(" ")?.get(0)!!
            ivAddStart.setOnClickListener {
                mListener.onClickAddStart(adapterPosition)
                if (firstTime) {
                    booking.passenger.score = oldScore
                }
                if (contStart != 3.0) {
                    contStart += 0.5
                    setStart(contStart)
                    booking.passenger.score =  (booking.passenger.score.plus(contStart))
                    firstTime = false
                }
            }
            ivRemoveStart.setOnClickListener {
                mListener.onClickRemoveStart(adapterPosition)
                if (firstTime) {
                    booking.passenger.score = oldScore
                }
                if (contStart != 0.0) {
                    contStart -= 0.5
                    setStart(contStart)
                    booking.passenger.score = (booking.passenger.score.plus(contStart))
                    firstTime = false
                }
            }
        }

        private fun setStart(contStart: Double) {
            when {
                contStart.equals(0.0) -> {
                    ivStart1.setImageResource(R.mipmap.withoutstart)
                    ivStart2.setImageResource(R.mipmap.withoutstart)
                    ivStart3.setImageResource(R.mipmap.withoutstart)
                }
                contStart.equals(0.5) -> {
                    ivStart1.setImageResource(R.mipmap.medstart)
                    ivStart2.setImageResource(R.mipmap.withoutstart)
                    ivStart3.setImageResource(R.mipmap.withoutstart)
                }
                contStart.equals(1.0) -> {
                    ivStart1.setImageResource(R.mipmap.star)
                    ivStart2.setImageResource(R.mipmap.withoutstart)
                    ivStart3.setImageResource(R.mipmap.withoutstart)
                }
                contStart.equals(1.5) -> {
                    ivStart1.setImageResource(R.mipmap.star)
                    ivStart2.setImageResource(R.mipmap.medstart)
                    ivStart3.setImageResource(R.mipmap.withoutstart)
                }
                contStart.equals(2.0) -> {
                    ivStart1.setImageResource(R.mipmap.star)
                    ivStart2.setImageResource(R.mipmap.star)
                    ivStart3.setImageResource(R.mipmap.withoutstart)
                }
                contStart.equals(2.5) -> {
                    ivStart1.setImageResource(R.mipmap.star)
                    ivStart2.setImageResource(R.mipmap.star)
                    ivStart3.setImageResource(R.mipmap.medstart)
                }
                contStart.equals(3.0) -> {
                    ivStart1.setImageResource(R.mipmap.star)
                    ivStart2.setImageResource(R.mipmap.star)
                    ivStart3.setImageResource(R.mipmap.star)
                }
            }
        }
    }
}