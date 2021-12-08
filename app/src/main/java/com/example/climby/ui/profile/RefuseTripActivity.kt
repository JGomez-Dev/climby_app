package com.example.climby.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.climby.databinding.ActivityRefuseTripBinding
import com.example.climby.ui.profile.viewmodel.RefuseTripViewModel

class RefuseTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRefuseTripBinding
    private lateinit var refuseTripViewModel: RefuseTripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refuseTripViewModel = ViewModelProvider(this).get(RefuseTripViewModel::class.java)
        binding = ActivityRefuseTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}