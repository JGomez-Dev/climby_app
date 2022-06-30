package com.app.climby.ui.publish

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.climby.R
import com.app.climby.data.model.trip.TripModel
import com.app.climby.databinding.ActivityWhatPlaceBinding
import com.app.climby.ui.profile.router.EditTripRouter
import com.app.climby.ui.publish.viewmodel.WhatPlaceViewModel
import com.app.climby.util.From
import com.app.climby.util.UIUtil
import com.app.climby.view.router.MainRouter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WhatPlaceActivity : AppCompatActivity() {

    private lateinit var whatPlaceViewModel: WhatPlaceViewModel
    private lateinit var binding: ActivityWhatPlaceBinding

    private lateinit var school: String
    private lateinit var from: String

    private var trip: TripModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whatPlaceViewModel = ViewModelProvider(this).get(WhatPlaceViewModel::class.java)
        binding = ActivityWhatPlaceBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getData()


        binding.ACSchool.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkControls()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                checkControls()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkControls()
            }
        })

        whatPlaceViewModel.schoolsModel.observe(this) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, it)
            binding.ACSchool.setAdapter(adapter)
            binding.ACSchool.requestFocus()
            UIUtil.showKeyboard(this)
        }

        binding.ACSchool.setOnItemClickListener { parent, _, position, _ ->
            school = parent.getItemAtPosition(position) as String
            binding.ACSchool.clearFocus();
            UIUtil.hideKeyboard(this)
        }

        binding.IVBack.setOnClickListener {
            UIUtil.hideKeyboard(this)
            onBackPressed()
        }

        binding.BTSave.setOnClickListener {
            if(from == "publish") {
                goToMainActivity()
                UIUtil.hideKeyboard(this)
            }
            else if(from == "editTrip"){
                goToEditTripActivity(trip!!, binding.ACSchool.text.toString())
                UIUtil.hideKeyboard(this)
            }
        }
        whatPlaceViewModel.getAllSchools()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun goToEditTripActivity(trip: TripModel, school: String) {
        EditTripRouter().launch(this, trip, school)
    }

    private fun goToMainActivity() {
        MainRouter().launch(this,
            binding.ACSchool.text.toString(),
            intent.extras?.getInt("province", 0),
            intent.extras?.getInt("type", 0),
            intent.extras?.getString("date", ""),
            intent.extras?.getString("dateWithoutFormat", ""),
            intent.extras?.getInt("place", 0),
            From.PUBLISH
        )
        finish()
    }

    private fun checkControls() {
        if (binding.ACSchool.text.toString().isNotEmpty()) {
            binding.BTSave.isEnabled = true
            binding.BTSave.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.primary))
        } else {
            binding.BTSave.isEnabled = false
            binding.BTSave.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.grey))
        }
    }

    private fun getData() {
        val bundle = intent.extras
        if (bundle != null) {
            school = bundle.getString("school", "")
            from = bundle.getString("from", "")
            trip = bundle.getParcelable("trip")
        }
    }
}