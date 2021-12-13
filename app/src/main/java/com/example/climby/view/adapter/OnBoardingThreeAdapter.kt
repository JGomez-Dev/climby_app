package com.example.climby.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import de.hdodenhof.circleimageview.CircleImageView

class OnBoardingThreeAdapter(listBokings: List<BookingModel>, context: Context) : RecyclerView.Adapter<OnBoardingThreeAdapter.DataViewHolder>() {

    private var listBokings: List<BookingModel> = ArrayList()
    private var context: Context

    init {
        this.listBokings = listBokings
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_qualify_attendees, parent, false)
    )

    override fun onBindViewHolder(holder: OnBoardingThreeAdapter.DataViewHolder, position: Int) {
        holder.bind(listBokings[position])
    }

    override fun getItemCount(): Int = listBokings.size

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cvAttendees: CircleImageView = itemView.findViewById(R.id.CVAttendees)
        private val tvNameQualifyAttendees: TextView = itemView.findViewById(R.id.TVNameQualifyAttendees)
        private val ivAddStart: ImageView = itemView.findViewById(R.id.IVAddStart)
        private val ivRemoveStart: ImageView = itemView.findViewById(R.id.IVRemoveStart)
        private val ivStart1: ImageView = itemView.findViewById(R.id.IVStart1)
        private val ivStart2: ImageView = itemView.findViewById(R.id.IVStart2)
        private val ivStart3: ImageView = itemView.findViewById(R.id.IVStart3)

        @SuppressLint("SetTextI18n")
        fun bind(booking: BookingModel) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_person_24).error(R.drawable.ic_baseline_person_24)).load(booking.passenger?.photo).into(cvAttendees)
            tvNameQualifyAttendees.text = booking.passenger?.name?.split(" ")?.get(0)!!
            ivAddStart.setOnClickListener {
                if (ivStart1.tag == R.mipmap.withoutstart) {
                    ivStart1.setImageResource(R.mipmap.medstart)
                }
                if (ivStart1.tag == R.mipmap.medstart) {
                    ivStart2.setImageResource(R.mipmap.star)
                }
                if (ivStart2.tag == R.mipmap.withoutstart) {
                    ivStart2.setImageResource(R.mipmap.medstart)
                }
                if (ivStart2.tag == R.mipmap.medstart) {
                    ivStart3.setImageResource(R.mipmap.star)
                }
                if (ivStart3.tag == R.mipmap.withoutstart) {
                    ivStart3.setImageResource(R.mipmap.medstart)
                }
                if (ivStart3.tag == R.mipmap.medstart) {
                    ivStart3.setImageResource(R.mipmap.star)
                }


            }
            ivRemoveStart.setOnClickListener {
                if (ivStart1.tag == R.mipmap.medstart) {
                    ivStart1.setImageResource(R.mipmap.withoutstart)
                }
                if (ivStart1.tag == R.mipmap.star) {
                    ivStart1.setImageResource(R.mipmap.medstart)
                }
                if (ivStart2.tag == R.mipmap.medstart) {
                    ivStart2.setImageResource(R.mipmap.withoutstart)
                }
                if (ivStart2.tag == R.mipmap.star) {
                    ivStart2.setImageResource(R.mipmap.medstart)
                }
                if (ivStart3.tag == R.mipmap.medstart) {
                    ivStart3.setImageResource(R.mipmap.withoutstart)
                }
                if (ivStart3.tag == R.mipmap.star) {
                    ivStart3.setImageResource(R.mipmap.medstart)
                }
            }
        }
    }
}