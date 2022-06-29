package com.app.climby.ui.publish

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.climby.R
import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.data.model.school.SchoolModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.types.TypesModel
import com.app.climby.databinding.FragmentPublishBinding
import com.app.climby.ui.publish.viewmodel.PublishViewModel
import com.app.climby.util.Commons
import com.app.climby.util.DatePickerFragment
import com.app.climby.view.activity.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PublishFragment : Fragment()/*, IOnBackPressed*/ {

    companion object {
        fun fragment() = PublishFragment()
    }

    private lateinit var publishViewModel: PublishViewModel
    private lateinit var binding: FragmentPublishBinding

    private var province: Int = 0
    private var type: Int = 0

    private var dateFormat = ""

    private var selectedType = -1
    private var selectedProvince = -1

    private var school: String? = ""
    private var date: String = ""
    private var places: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        publishViewModel = ViewModelProvider(this)[PublishViewModel::class.java]
        binding = FragmentPublishBinding.inflate(layoutInflater)
        val view: View = binding.root

        val navBar: BottomNavigationView = activity?.findViewById(R.id.nav_view)!!
        navBar.isVisible = false

        if(!Commons.isInternetAvailable(requireContext().applicationContext)){
            binding.CLNotConnection.isVisible = true
        }else{
            binding.CLNotConnection.isVisible = false

            binding.ETSite.setOnClickListener {
                loadActivity()
            }

            publishViewModel.provincesModel.observe(viewLifecycleOwner) {
                setupAdapterProvinces(it)
                if (province != 0)
                    binding.SPCommunity.setSelection(province)

            }

            publishViewModel.typesModel.observe(viewLifecycleOwner) {
                setupAdapterType(it)
                if (type != 0)
                    binding.SPType.setSelection(type)
            }

            binding.ETDate.setOnClickListener {
                showDatePickerDialog()
            }

            binding.BTNewExit.setOnClickListener {
                val tripModel = TripModel(0, SchoolModel(binding.ETSite.text.toString()), TypesModel(binding.SPType.selectedItem.toString()), binding.SPPlacesAvailable.selectedItem.toString().toInt(), dateFormat, ProvinceModel(binding.SPCommunity.selectedItem.toString(), 0), Commons.userSession, arrayListOf())
                saveTrip(tripModel)
            }

            publishViewModel.tripCreated.observe(viewLifecycleOwner) {
                showMainActivity()
            }

            binding.SPPlacesAvailable.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position == 0) {
                        (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                    }else{
                         (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                     }
                    checkControls()
                    places = parent.getItemIdAtPosition(position).toInt()
                }
            }

            publishViewModel.getProvince()
            publishViewModel.getTypes()

            if (arguments != null) {
                school = arguments?.getString("schoolPublish", "")
                province = arguments?.getInt("provincePublish", 0)!!
                type = arguments?.getInt("typePublish", 0)!!
                date = arguments?.getString("datePublish", "")!!
                dateFormat = arguments?.getString("datePublishWithOutFormat", "")!!
                places = arguments?.getInt("placePublish", 0)!!

                binding.ETSite.text = school
                binding.ETSite.setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))

                if (date != "DD/MM") {
                    binding.ETDate.setText(date)
                    binding.ETDate.setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }
                binding.SPPlacesAvailable.setSelection(places)
            } else {
                binding.ETSite.text = getString(R.string.text_select_school)
            }
        }
        binding.IVBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return view
    }

    private fun showMainActivity() {
       /* val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)*/
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("from", "profile")
        }
        startActivity(intent)
        activity?.overridePendingTransition(0, R.anim.slide_in_down )
    }



    private fun saveTrip(tripModel: TripModel) {
        publishViewModel.saveTrip(tripModel)
        /*val database = Firebase.database.getReference("travels")
        database.child("travel").child(tripModel.id.toString()).setValue(tripModel)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    var user = childSnapshot.getValue(TripModel::class.java)
                }
                val post = dataSnapshot.value
                val builder = NotificationCompat.Builder(requireActivity().applicationContext, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.splash)
                    .setContentTitle("textTitle")
                    .setContentText("textContent")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                with(NotificationManagerCompat.from(requireActivity().applicationContext)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(1, builder.build())
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("NotificationModel", "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)*/
        /*NotificationClass.saveTravel(tripModel)*/
    }

    private fun checkControls() {
        if (binding.ETDate.text.toString() != "DD/MM" && binding.SPCommunity.selectedItem != "Elige tu provincia" && binding.SPType.selectedItem != "Selecciona el tipo de Escalada" && binding.ETSite.text != "Elige una escuela o rocódromo…" && binding.SPPlacesAvailable.selectedItem != "0") {
            binding.BTNewExit.isEnabled = true
            binding.BTNewExit.setBackgroundColor(ContextCompat.getColor(requireContext().applicationContext, R.color.primary))
        } else {
            binding.BTNewExit.isEnabled = false
            binding.BTNewExit.setBackgroundColor(ContextCompat.getColor(requireContext().applicationContext, R.color.disable))
        }
    }

    private fun setupAdapterType(it: List<String>) {

        binding.SPType.setSelection(0, true)
        val v: View = binding.SPType.selectedView
        (v as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.grey))

        val arrayAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_dropdown_item, it) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val row: View
                if (position == 0) {
                    row = super.getDropDownView(position, convertView, parent) as TextView
                    row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    row.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    row = super.getDropDownView(position, null, parent) as TextView
                    if (position == selectedType) {
                        row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_light))
                    }
                    row.setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }
                return row
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }


        arrayAdapter.notifyDataSetChanged()
        binding.SPType.adapter = arrayAdapter
        binding.SPType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.grey))
                }else{
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }
                selectedType = position
                checkControls()
                type = parent.getItemIdAtPosition(position).toInt()
            }
        }
    }

    private fun setupAdapterProvinces(it: List<String>) {
        val arrayAdapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_dropdown_item, it) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val row: View
                if (position == 0) {
                    row = super.getDropDownView(position, convertView, parent) as TextView
                    row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    row.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    row = super.getDropDownView(position, null, parent) as TextView
                    if (position == selectedProvince) {
                        row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_light))
                    }
                    row.setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }

                return row
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        arrayAdapter.notifyDataSetChanged()
        binding.SPCommunity.adapter = arrayAdapter
        binding.SPCommunity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.grey))
                }else{
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
                }
                selectedProvince = position
                checkControls()
                province = parent.getItemIdAtPosition(position).toInt()
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment({ day, month, year ->
            onDateSelected(day, month + 1, year)
        }, dateFormat)
        datePicker.show(childFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.ETDate.setText("$day/$month")
        val monthFormat = if (month.toString().length < 2)
            "0$month"
        else
            month.toString()
        val dayFormat = if (day.toString().length < 2)
            "0$day"
        else
            day.toString()
        dateFormat = "$year-$monthFormat-$dayFormat 01:01:01"
        binding.ETDate.setTextColor(ContextCompat.getColor(requireContext().applicationContext, R.color.black))
        checkControls()
    }

    private fun loadActivity() {
        activity?.let {
            val intent = Intent(it, WhatPlaceActivity::class.java).apply {
                putExtra("schoolPublish", binding.ETSite.text)
                putExtra("provincePublish", binding.SPCommunity.selectedItemId.toInt())
                putExtra("typePublish", binding.SPType.selectedItemId.toInt())
                putExtra("datePublish", binding.ETDate.text.toString())
                putExtra("datePublishWithOutFormat", dateFormat)
                putExtra("placePublish", binding.SPPlacesAvailable.selectedItemId.toInt())
                putExtra("from", "publish")
            }
            it.startActivity(intent)
            it.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    /*override fun onBackPressed() {
        activity?.let {
            val intent = Intent(requireContext().applicationContext, MainActivity::class.java)
            it.startActivity(intent)
            it.overridePendingTransition(0, 0)
            it.finish()
        }
    }*/
}
