package com.jgomez.discover_presentation.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgomez.discover_domain.model.Trip
import com.jgomez.discover_presentation.databinding.ItemDiscoverBinding

class TripsAdapter: RecyclerView.Adapter<TripsAdapter.MyViewHolder>() {

    private var list = listOf<Trip>()

    fun setData(list: List<Trip>){
        this.list = list
        notifyItemInserted(this.list.lastIndex)
    }

    inner class MyViewHolder(val viewDataBinding: ItemDiscoverBinding) : RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding = ItemDiscoverBinding.inflate(LayoutInflater.from(parent.context), parent, true)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewDataBinding.apply {
            val item = list[position]
            TVType.text = item.climbType.name
        }
    }

    override fun getItemCount(): Int = this.list.size
}