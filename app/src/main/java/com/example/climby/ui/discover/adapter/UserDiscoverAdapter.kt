package com.example.climby.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.climby.data.model.user.UserModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList
import com.bumptech.glide.request.RequestOptions
import com.example.climby.R
import com.example.climby.data.model.booking.BookingModel


class UserDiscoverAdapter(bookingsList: List<BookingModel>, context: Context) : RecyclerView.Adapter<UserDiscoverAdapter.DataViewHolder>() {

    private var bookingsList: List<BookingModel> = ArrayList()
    private var context: Context

    init {
        this.bookingsList = bookingsList
        this.context = context
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val cvPassenger: CircleImageView = itemView.findViewById(R.id.CVPassenger)

        fun bind(result: BookingModel){
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(result.passenger.photo).into(cvPassenger)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_discover_user, parent, false)
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(bookingsList[position])
    }

    override fun getItemCount(): Int= bookingsList.size

}