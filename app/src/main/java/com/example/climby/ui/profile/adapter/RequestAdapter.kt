package com.example.climby.ui.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.utils.ReservationStatus
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RequestAdapter(bookingData: List<BookingModel>, context: Context) : RecyclerView.Adapter<RequestAdapter.DataViewHolder>() {

    private var bookingData: List<BookingModel> = ArrayList()
    private var context: Context = context
    private lateinit var mListener: OnClickListener

    init {
        this.bookingData = bookingData
        this.context = context
    }

    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onClickRefuse(position: Int)
        fun onClickContact(position: Int)
        fun onClickAcept(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }


    inner class DataViewHolder(itemView: View, listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val cIPhotoUser: CircleImageView = itemView.findViewById(R.id.CIPhotoUser)
        private val tVUserNameRequest: TextView = itemView.findViewById(R.id.TVUserNameRequest)
        private val iVStart1: ImageView = itemView.findViewById(R.id.IVStart1)
        private val iVStart2: ImageView = itemView.findViewById(R.id.IVStart2)
        private val iVStart3: ImageView = itemView.findViewById(R.id.IVStart3)
        private val tVUserExperience: TextView = itemView.findViewById(R.id.TVUserExperience)
        private val tVUserOutputs: TextView = itemView.findViewById(R.id.TVUserOutputs)
        private val bTRefuseRquest: Button = itemView.findViewById(R.id.BTRefuseRquest)
        private val bTAceptRquest: Button = itemView.findViewById(R.id.BTAceptRquest)
        private val btContact: Button = itemView.findViewById(R.id.BtContact)

        @SuppressLint("SetTextI18n")
        fun bind(booking: BookingModel) {
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_baseline_person_24).error(R.drawable.ic_baseline_person_24)).load(booking.passenger?.photo).into(cIPhotoUser)
            tVUserNameRequest.text = booking.passenger?.name?.split(" ")?.get(0) ?: ""
            if(booking.status == ReservationStatus.ACCEPTED.status ){
                btContact.isVisible = true
            }
            bTRefuseRquest.setOnClickListener {
                mListener.onClickRefuse(adapterPosition)
            }
            bTAceptRquest.setOnClickListener {
                mListener.onClickAcept(adapterPosition)
            }
            btContact.setOnClickListener {
                mListener.onClickContact(adapterPosition)
            }
            tVUserExperience.text = booking.passenger?.experience
            tVUserOutputs.text = if (booking.passenger?.outings.toString() == "1") {
                booking.passenger?.outings.toString() + " salida"
            } else {
                booking.passenger?.outings.toString() + " salidas"
            }
            booking.passenger?.let { setStart(it) }
        }

        private fun setStart(passenger: UserModel) {
            when {
                passenger.score > 2.75 -> {
                    iVStart1.setImageResource(R.mipmap.star)
                    iVStart2.setImageResource(R.mipmap.star)
                    iVStart3.setImageResource(R.mipmap.star)
                }
                passenger.score in 2.25..2.75 -> {
                    iVStart1.setImageResource(R.mipmap.star)
                    iVStart2.setImageResource(R.mipmap.star)
                    iVStart3.setImageResource(R.mipmap.medstart)
                }
                passenger.score in 1.75..2.25 -> {
                    iVStart1.setImageResource(R.mipmap.star)
                    iVStart2.setImageResource(R.mipmap.star)
                    iVStart3.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score in 1.25..1.75 -> {
                    iVStart1.setImageResource(R.mipmap.star)
                    iVStart2.setImageResource(R.mipmap.medstart)
                    iVStart3.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score in 0.75..1.25 -> {
                    iVStart1.setImageResource(R.mipmap.star)
                    iVStart2.setImageResource(R.mipmap.withoutstart)
                    iVStart3.setImageResource(R.mipmap.withoutstart)
                }
                passenger.score <= 0.75 -> {
                    iVStart1.setImageResource(R.mipmap.medstart)
                    iVStart2.setImageResource(R.mipmap.withoutstart)
                    iVStart3.setImageResource(R.mipmap.withoutstart)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_request, parent, false), mListener)

    override fun onBindViewHolder(holder: RequestAdapter.DataViewHolder, position: Int): Unit = holder.bind(bookingData[position])

    override fun getItemCount(): Int = bookingData.size

}
