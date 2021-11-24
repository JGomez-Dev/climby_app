package com.example.climby.view.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.climby.R
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ActivityOnboardingSecondBinding
import com.example.climby.utils.UserExperience
import com.example.climby.view.viewmodel.OnBoardingSecondViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingSecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingSecondBinding
    private lateinit var onBoardingSecondViewModel: OnBoardingSecondViewModel
    private var email: String? = null
    private var provider: String? = null
    private var photoUrl: String? = null
    private var displayName: String? = null
    private var phone: String? = null
    private var userExperience: String = "Principiante"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingSecondViewModel = ViewModelProvider(this).get(OnBoardingSecondViewModel::class.java)
        binding = ActivityOnboardingSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.CVBeginner.setOnClickListener {
            selectedBeginner()
        }
        binding.CVIntermediate.setOnClickListener {
            selectedIntermediate()
        }
        binding.CVExperienced.setOnClickListener {
            selectedExperienced()
        }
        binding.BTNext.setOnClickListener {
            insertUser()
            showMainActivity()
        }
    }

    private fun selectedExperienced() {
        binding.RBExperienced.isChecked = true
        binding.RBBeginner.isChecked = false
        binding.RBIntermediate.isChecked = false
        binding.CVExperienced.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.TVExperienced.setTextColor(ContextCompat.getColor(this, R.color.primary))
        binding.CVBeginner.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.TVBeginner.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.CVIntermediate.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.TVIntermediate.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.BTNext.isEnabled = true
        userExperience = UserExperience.ADVANCED.status
    }

    private fun selectedIntermediate() {
        binding.RBIntermediate.isChecked = true
        binding.RBBeginner.isChecked = false
        binding.RBExperienced.isChecked = false
        binding.CVIntermediate.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.TVIntermediate.setTextColor(ContextCompat.getColor(this, R.color.primary))
        binding.CVBeginner.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.TVBeginner.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.CVExperienced.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.TVExperienced.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.BTNext.isEnabled = true
        userExperience = UserExperience.MEDIUM.status
    }

    private fun selectedBeginner() {
        binding.RBBeginner.isChecked = true
        binding.RBIntermediate.isChecked = false
        binding.RBExperienced.isChecked = false
        binding.CVBeginner.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        binding.TVBeginner.setTextColor(ContextCompat.getColor(this, R.color.primary))
        binding.CVIntermediate.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.TVIntermediate.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.CVExperienced.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.TVExperienced.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.BTNext.isEnabled = true
        userExperience = UserExperience.BEGINNER.status
    }

    private fun getData() {
        val bundle = intent.extras
        email = bundle?.getString("email")
        provider = bundle?.getString("provider")
        photoUrl = bundle?.getString("photoUrl")
        displayName = bundle?.getString("displayName")
        phone = bundle?.getString("phone")
    }

    private fun showMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun insertUser() {
        onBoardingSecondViewModel.postUser(UserModel(0, displayName.toString(), getExperience(userExperience), phone.toString(), email.toString(), 0.0, 0, photoUrl.toString()))
    }

    private fun getExperience(userExperience: String): String {
        when (userExperience) {
            UserExperience.BEGINNER.status -> return "Principiante"
            UserExperience.MEDIUM.status -> return "Intermedio"
            UserExperience.ADVANCED.status -> return "Experimentado"
        }
        return "Principiante"
    }
}