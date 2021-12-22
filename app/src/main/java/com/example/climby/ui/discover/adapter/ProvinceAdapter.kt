package com.example.climby.ui.discover.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.climby.R
import com.example.climby.data.model.province.ProvinceTripsModel


class ProvinceAdapter(tripData: List<ProvinceTripsModel>, province: String?, context: Context) : RecyclerView.Adapter<ProvinceAdapter.DataViewHolder>() {

    private var tripsList: List<ProvinceTripsModel> = ArrayList()
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


    inner class DataViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val tvCommunitySP: TextView = itemView.findViewById(R.id.TVCommunitySP)
        private val tvNumberSP: TextView = itemView.findViewById(R.id.TVNumberSP)

        fun bind(province: ProvinceTripsModel) {
            if (provinceSelected?.equals(province.name)!!) {
                tvCommunitySP.setTextColor(ContextCompat.getColorStateList(context, R.color.primary))
                tvNumberSP.setTextColor(ContextCompat.getColorStateList(context, R.color.primary))
/*
                tvNumberSP.requestFocus()
*/
            }else {
                tvCommunitySP.setTextColor(ContextCompat.getColorStateList(context, R.color.grey_dark))
                tvNumberSP.setTextColor(ContextCompat.getColorStateList(context, R.color.grey))
            }
            tvCommunitySP.text = province.name
            tvNumberSP.text = province.tripsNumber.toString()
        }
    }
    


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.custom_spinner_item, parent, false), mlistener
    )

    /*override fun onViewDetachedFromWindow(holder: DataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        tripsList.forEach {
            if(it.name == provinceSelected){
                holder.itemView.findViewById<TextView>(R.id.TVCommunitySP).requestFocus()
            }
        }

    }*/

   /* override fun onViewAttachedToWindow(holder: DataViewHolder) {
        super.onViewAttachedToWindow(holder)
        tripsList.forEach {
            if(it.name == provinceSelected){
                holder.itemView.findViewById<TextView>(R.id.TVCommunitySP).requestFocus()
            }
        }

    }*/

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tripsList[position])
    }



    override fun getItemCount(): Int = tripsList.size

}