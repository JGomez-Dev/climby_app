package com.example.climby.ui.discover.adapter

import com.example.climby.data.model.province.ProvinceTripsModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.climby.R

class CustomDropDownAdapter(val context: Context, var dataSource: List<ProvinceTripsModel>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        vh.tvCommunitySP.text = dataSource[position].name
        vh.tvNumberSP.text = dataSource[position].tripsNumber.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val tvCommunitySP: TextView = row?.findViewById(R.id.TVCommunitySP) as TextView
        val tvNumberSP: TextView = row?.findViewById(R.id.TVNumberSP) as TextView

    }

}