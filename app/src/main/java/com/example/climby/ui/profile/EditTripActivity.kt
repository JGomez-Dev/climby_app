package com.example.climby.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.databinding.ActivityEditTripBinding
import com.example.climby.ui.profile.viewmodel.EditTripViewModel
import com.example.climby.utils.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonCancellable.cancel


@AndroidEntryPoint
class EditTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTripBinding
    private lateinit var editTripViewModel: EditTripViewModel
    private var trip: TripModel? = null

    private var selectedProvince = -1
    private var selectedType = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTripViewModel = ViewModelProvider(this).get(EditTripViewModel::class.java)
        binding = ActivityEditTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.ETDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }

        binding.BTDeleteExit.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.text_sure)
            .setMessage(R.string.text_asistance)
            .setNegativeButton(R.string.cancel) { view, _ ->
                Toast.makeText(this,"Cancelar",Toast.LENGTH_SHORT ).show()
                view.dismiss()
            }
            .setPositiveButton(R.string.text_delete) { view, _ ->
                Toast.makeText(this,"Eliminar",Toast.LENGTH_SHORT ).show()
                view.dismiss()
            }
            .setCancelable(false)
            .create().show()
    }


    private fun getData() {
        val bundle = intent.extras
        trip = bundle?.getParcelable("trip")
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.ETSite.setText(trip?.site?.name)
        editTripViewModel.getTypes()
        editTripViewModel.getProvince()
        editTripViewModel.typesModel.observe(this, {
            /*val adapter = ArrayAdapter(this, com.example.climby.R.layout.support_simple_spinner_dropdown_item, it)
            binding.SPType.adapter = adapter
            binding.SPType.setSelection(adapter.getPosition(trip?.type?.name.toString()))*/
            setupAdapterType(it)
        })
        editTripViewModel.provincesModel.observe(this, Observer {
            /*val adapter = ArrayAdapter(this, com.example.climby.R.layout.support_simple_spinner_dropdown_item, it)
            binding.SPCommunity.adapter = adapter
            binding.SPCommunity.setSelection(adapter.getPosition(trip?.province?.name.toString()))*/
            setupAdapterProvinces(it)
        })
        trip?.availablePlaces?.let { binding.SPPlacesAvailable.setSelection(it - 1) }
        binding.ETDate.setText((trip?.departure?.split("-")?.get(2)?.split(" ")?.get(0) ?: "") + "/" + (trip?.departure?.split("-")?.get(1) ?: ""))
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
        val datePicker = DatePickerFragment ( {
                day, month, _ -> onDateSelected(day, month + 1)
        }, "dateFormat")
        datePicker.show(supportFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(day: Int, month: Int) {
        binding.ETDate.setText("$day/$month")
    }
}