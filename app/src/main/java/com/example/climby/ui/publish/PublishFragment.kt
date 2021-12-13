package com.example.climby.ui.publish

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climby.R
import com.example.climby.data.model.province.ProvinceModel
import com.example.climby.data.model.school.SchoolModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.types.TypesModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.FragmentPublishBinding
import com.example.climby.ui.publish.viewmodel.PublishViewModel
import com.example.climby.utils.Commons
import com.example.climby.utils.DatePickerFragment
import com.example.climby.utils.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishFragment : Fragment(), IOnBackPressed {

    private lateinit var publishViewModel: PublishViewModel
    private lateinit var binding: FragmentPublishBinding

    private var province: Int = 0
    private var type: Int = 0
    private var contC = 0
    private var contT = 0
    private var dateFormat = ""

    private var userSession: UserModel? = Commons.userSession!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        publishViewModel = ViewModelProvider(this).get(PublishViewModel::class.java)
        binding = FragmentPublishBinding.inflate(layoutInflater)
        val view: View = binding.root


        binding.ETSite.setOnClickListener {
            loadFragment()
        }

        /*binding.IVBack.setOnClickListener {
            onBackPressed()
        }*/

        publishViewModel.provincesModel.observe(viewLifecycleOwner, Observer {
            setupAdapterProvinces(it)
        })

        publishViewModel.typesModel.observe(viewLifecycleOwner, Observer {
            setupAdapterType(it)
        })

        binding.ETDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.BTNewExit.setOnClickListener {
            val tripModel = TripModel(0, SchoolModel(binding.ETSite.text.toString()), TypesModel(binding.SPType.selectedItem.toString()), binding.SPPlacesAvailable.selectedItem.toString().toInt(), dateFormat, ProvinceModel(binding.SPCommunity.selectedItem.toString()), userSession, arrayListOf())
            saveTrip(tripModel)
        }

        publishViewModel.tripCreated.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity().applicationContext,"Creado", Toast.LENGTH_SHORT ).show()
        })

        publishViewModel.getProvince()
        publishViewModel.getTypes()


        return view
    }

    private fun saveTrip(tripModel: TripModel){
        publishViewModel.saveTrip(tripModel)
    }

    private fun checkControls() {
        if (binding.ETDate.text.toString() != "DD/MM" && binding.SPCommunity.selectedItem != "Elige tu provincia" && binding.SPType.selectedItem!= "Boulder, Deportiva, Rocódromo..." /*&& binding.ETSite.text != "Elige una escuela o rocódromo…"*/) {
            binding.BTNewExit.isEnabled = true
            binding.BTNewExit.setBackgroundColor(ContextCompat.getColor(requireContext().applicationContext, R.color.primary))
        } else {
            binding.BTNewExit.isEnabled = false
            binding.BTNewExit.setBackgroundColor(ContextCompat.getColor(requireContext().applicationContext, R.color.grey_light))
        }
    }

    private fun setupAdapterType(it: List<String>) {
        val arrayAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, it) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0)
                    view.setTextColor(ContextCompat.getColor(context, R.color.grey))
                return view
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        binding.SPType.adapter = arrayAdapter
        binding.SPType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (contT == 0) {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.grey))

                } else {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }
                checkControls()
                contT++
                type = parent.getItemIdAtPosition(position).toInt()
            }
        }
    }

    private fun setupAdapterProvinces(it: List<String>) {
        val arrayAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, it) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0)
                    view.setTextColor(ContextCompat.getColor(context, R.color.grey))
                return view
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        binding.SPCommunity.adapter = arrayAdapter
        binding.SPCommunity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (contC == 0) {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.grey))

                } else {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }
                checkControls()
                contC++
                province = parent.getItemIdAtPosition(position).toInt()
            }
        }
    }


    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month + 1, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.ETDate.setText("$day/$month")
        val monthFormat = if(month.toString().length < 2)
            "0$month"
        else
            month.toString()
        val dayFormat = if(day.toString().length < 2)
            "0$day"
        else
            day.toString()
        dateFormat = "$year-$monthFormat-$dayFormat 01:01:01"
        binding.ETDate.setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
        checkControls()
    }

    private fun loadFragment() {
        val intent = Intent(activity, WhatPlaceActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        /*activity?.let {
            val intent = Intent (it, MainActivity::class.java)
            it.startActivity(intent)
            it.finish()
        }*/
        /*activity?.supportFragmentManager?.popBackStack();*/
    }
}
