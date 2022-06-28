package com.app.climby.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.climby.R
import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.databinding.CustomSpinnerItemBinding
import com.app.climby.databinding.ItemDiscoverBinding


class ProvinceAdapter(tripData: List<ProvinceModel>, province: String?, context: Context) : RecyclerView.Adapter<ProvinceAdapter.DataViewHolder>() {

    private var tripsList: List<ProvinceModel> = ArrayList()
    private var context: Context
    private var provinceSelected: String?
    private lateinit var mlistener: OnItemClickListener

    init {
        this.tripsList = tripData
        this.context = context
        this.provinceSelected = province
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.custom_spinner_item, parent, false), mlistener
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tripsList[position])
    }

    override fun getItemCount(): Int = tripsList.size

    inner class DataViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val binding = CustomSpinnerItemBinding.bind(itemView)

        fun bind(province: ProvinceModel) = with(binding) {
            if (provinceSelected?.equals(province.name)!!) {
                TVCommunitySP.setTextColor(ContextCompat.getColorStateList(context, R.color.primary))
                TVNumberSP.setTextColor(ContextCompat.getColorStateList(context, R.color.primary))
            }else {
                TVCommunitySP.setTextColor(ContextCompat.getColorStateList(context, R.color.grey_dark))
                TVNumberSP.setTextColor(ContextCompat.getColorStateList(context, R.color.grey))
            }
            TVCommunitySP.text = province.name
            TVNumberSP.text = province.number_travels.toString()
        }
    }
}