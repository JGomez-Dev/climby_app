package com.example.climby.ui.publish

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climby.R
import com.example.climby.databinding.FragmentPublishBinding
import com.example.climby.ui.publish.viewmodel.PublishViewModel
import com.example.climby.utils.DatePickerFragment
import com.example.climby.utils.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PublishFragment : Fragment(), IOnBackPressed{

    private lateinit var publishViewModel: PublishViewModel
    private lateinit var binding: FragmentPublishBinding
    private var province: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        publishViewModel = ViewModelProvider(this).get(PublishViewModel::class.java)
        binding = FragmentPublishBinding.inflate(layoutInflater)
        val view: View = binding.root

        binding.ETSite.setOnClickListener {
            loadFragment()
        }

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }

        publishViewModel.provincesModel.observe(viewLifecycleOwner, Observer {
            binding.SPCommunity.adapter = activity?.let { context ->
                ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, it)
            }
            binding.SPCommunity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (parent != null) {
                        province = parent.getItemIdAtPosition(position).toInt()
                    }
                }
            }
        })

        publishViewModel.typesModel.observe(viewLifecycleOwner, Observer {
            binding.SPType.adapter = activity?.let { context ->
                ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, it)
            }
        })

        binding.ETDate.setOnClickListener {
            showDatePickerDialog()
        }

        publishViewModel.getProvince()
        publishViewModel.getTypes()


        return view
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month + 1, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.ETDate.setText("$day/$month")
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
