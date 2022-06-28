package com.app.climby.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList
import com.bumptech.glide.request.RequestOptions
import com.app.climby.R
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.databinding.ItemDiscoverUserBinding


class UserDiscoverAdapter(bookingsList: List<BookingModel>, context: Context) : RecyclerView.Adapter<UserDiscoverAdapter.DataViewHolder>() {

    private var bookingsList: List<BookingModel> = ArrayList()
    private var context: Context

    init {
        this.bookingsList = bookingsList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_discover_user, parent, false)
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(bookingsList[position])
    }

    override fun getItemCount(): Int= bookingsList.size

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDiscoverUserBinding.bind(itemView)
        fun bind(result: BookingModel)= with(binding){
            Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.mipmap.user).error(R.mipmap.user)).load(result.passenger?.photo).into(CVPassenger)
        }
    }
}