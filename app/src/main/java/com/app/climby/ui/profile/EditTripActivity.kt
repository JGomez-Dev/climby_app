package com.app.climby.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.climby.R
import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.data.model.school.SchoolModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.types.TypesModel
import com.app.climby.databinding.ActivityEditTripBinding
import com.app.climby.ui.profile.viewmodel.EditTripViewModel
import com.app.climby.ui.publish.WhatPlaceActivity
import com.app.climby.utils.Commons
import com.app.climby.utils.DatePickerFragment
import com.app.climby.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTripBinding
    private lateinit var editTripViewModel: EditTripViewModel
    private var trip: TripModel? = null
    private var school: String? = null
    private var selectedProvince = -1
    private var selectedType = -1
    private var places: Int = 0

    private var dateFormat = ""

    private var province: Int = 0
    private var type: Int = 0

    private var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTripViewModel = ViewModelProvider(this)[EditTripViewModel::class.java]
        binding = ActivityEditTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()
        checkControls()

        binding.ETDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.ETSite.setOnClickListener {
            loadFragment()
        }

        binding.IVBack.setOnClickListener {
            onBackPressed()
            overridePendingTransition(0, R.anim.slide_in_down);
        }

        binding.BTDeleteExit.setOnClickListener {
            showDialog()
        }

        binding.SPPlacesAvailable.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO hacer algo
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
                checkControls()
                places = parent.getItemIdAtPosition(position).toInt()
            }
        }

        binding.BTNewExit.setOnClickListener {
            val tripModel = TripModel(trip!!.id, SchoolModel(binding.ETSite.text.toString()), TypesModel(binding.SPType.selectedItem.toString()), places + 1, dateFormat, ProvinceModel(binding.SPCommunity.selectedItem.toString(), 0), Commons.userSession, arrayListOf())
            editTripViewModel.updateTrip(tripModel)
            showMainActivity()
        }
    }

    private fun showMainActivity() {
        val intent = Intent(applicationContext.applicationContext, MainActivity::class.java).apply {
            putExtra("exprienceProfile", Commons.userSession?.experience)
        }
        startActivity(intent)
        overridePendingTransition(0, R.anim.slide_out_right)
    }

    private fun loadFragment() {
        val intent = Intent(this, WhatPlaceActivity::class.java).apply {
            putExtra("schoolPublish", binding.ETSite.text)
            putExtra("provincePublish", binding.SPCommunity.selectedItemId.toInt())
            putExtra("typePublish", binding.SPType.selectedItemId.toInt())
            putExtra("datePublish", binding.ETDate.text.toString())
            putExtra("datePublishWithOutFormat", dateFormat)
            putExtra("placePublish", binding.SPPlacesAvailable.selectedItemId.toInt())
            putExtra("trip", trip)
            putExtra("from", "editTrip")
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.text_sure)
            .setMessage(R.string.text_asistance)
            .setNegativeButton(R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setPositiveButton(R.string.text_delete) { view, _ ->
                deleteTrip()
                view.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }

    private fun checkControls() {
        if (binding.ETDate.text.toString() != "DD/MM" && binding.SPCommunity.selectedItem != "Elige tu provincia" && binding.SPType.selectedItem != "Selecciona el tipo de Escalada" && binding.ETSite.text != "Elige una escuela o rocódromo…" && binding.SPPlacesAvailable.selectedItem != "0") {
            binding.BTNewExit.isEnabled = true
            binding.BTNewExit.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.primary))
        } else {
            binding.BTNewExit.isEnabled = false
            binding.BTNewExit.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.disable))
        }
    }

    private fun deleteTrip() {
        editTripViewModel.deleteTrip(trip!!)
        if(trip!!.bookings!!.size != 0)
        {
            trip!!.bookings!!.forEach {
                Commons.sendNotification(it.passenger?.token!!,
                    trip!!.driver?.name!!.split(" ")[0] + " ha eliminado el viaje.",
                    "AuthActivity",
                    trip!!.id.toString(),
                    "RequestsActivity",
                    trip!!.driver?.name!!.split(" ")[0] + " ha eliminado el viaje a " + trip!!.site?.name + " el " + trip!!.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip!!.departure.toString() + "."),
                    this,
                    this
                )
            }
        }
        showMainActivity()
    }

    private fun getData() {
        val bundle = intent.extras
        if (bundle != null) {
            trip = bundle.getParcelable("trip")
            school = bundle.getString("schoolPublish")

        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.ETSite.text = trip?.site?.name
        if(school != null){
            binding.ETSite.text = school
        }
        binding.ETSite.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        editTripViewModel.getTypes()
        editTripViewModel.getProvince()
        editTripViewModel.typesModel.observe(this) {
            setupAdapterType(it)
        }
        editTripViewModel.provincesModel.observe(this, Observer {
            setupAdapterProvinces(it)
        })

        trip?.availablePlaces?.let { binding.SPPlacesAvailable.setSelection(it - 1) }
        binding.ETDate.setText((trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + "/" + (trip?.departure?.split("-")?.get(1) ?: ""))
        binding.ETDate.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
        dateFormat = trip?.departure.toString()
    }

    private fun setupAdapterProvinces(it: List<String>) {
        val arrayAdapter = object : ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val row: View
                if (position == 0) {
                    row = super.getDropDownView(position, convertView, parent) as TextView
                    row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    row.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    row = super.getDropDownView(position, null, parent)
                    if (position == selectedProvince) {
                        row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_light))
                    }
                }
                return row
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        binding.SPCommunity.adapter = arrayAdapter
        binding.SPCommunity.setSelection(arrayAdapter.getPosition(trip?.province?.name.toString()))
        binding.SPCommunity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO algo
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
                }
                selectedProvince = position
                /*checkControls()*/
                /*province = parent!!.getItemIdAtPosition(position).toInt()*/
            }
        }
    }

    private fun setupAdapterType(it: List<String>) {

        val arrayAdapter = object : ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val row: View

                if (position == 0) {
                    row = super.getDropDownView(position, convertView, parent) as TextView
                    row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary))
                    row.setTextColor(ContextCompat.getColor(context, R.color.white))
                } else {
                    row = super.getDropDownView(position, null, parent)
                    if (position == selectedType) {
                        row.setBackgroundColor(ContextCompat.getColor(context, R.color.primary_light))
                    }
                }
                return row
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }
        arrayAdapter.notifyDataSetChanged()
        binding.SPType.adapter = arrayAdapter
        binding.SPType.setSelection(arrayAdapter.getPosition(trip?.type?.name.toString()))
        binding.SPType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO algo
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    (parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
                }
                selectedType = position
                /*checkControls()
                type = parent!!.getItemIdAtPosition(position).toInt()*/
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment({ day, month, year ->
            onDateSelected(day, month + 1, year)
        }, dateFormat)
        datePicker.show(supportFragmentManager, "datePicker")
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
        binding.ETDate.setTextColor(ContextCompat.getColor(this, R.color.black))
    }
}