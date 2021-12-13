package com.example.climby.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.climby.databinding.ActivityOnboardingThreeBinding
import com.example.climby.view.viewmodel.OnBoardingThreeViewModel
class OnBoardingThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingThreeBinding
    private lateinit var onBoardingThreeViewModel: OnBoardingThreeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingThreeViewModel = ViewModelProvider(this).get(OnBoardingThreeViewModel::class.java)
        binding = ActivityOnboardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}