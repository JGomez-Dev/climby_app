package com.app.climby.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ActivityOnboardingSecondBinding
import com.app.climby.util.UIUtil
import com.app.climby.util.UserExperience
import com.app.climby.view.router.MainRouter
import com.app.climby.view.viewmodel.OnBoardingSecondViewModel
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

        //checkPermissionSms()

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
            onBoardingSecondViewModel.generateToken()
            onBoardingSecondViewModel.token.observe(this, Observer { token ->
                insertUser(token)
            })
        }
        onBoardingSecondViewModel.okSaveUser.observe(this, Observer { it ->
            if (it)
                showMainActivity()
            else
                Toast.makeText(this, "Problema en el servidor", Toast.LENGTH_SHORT).show()
        })

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        showOnBoardingFirst(email, photoUrl, displayName)
    }

    private fun showOnBoardingFirst(email: String?, photoUrl: String?, displayName: String?) {
        val intent = Intent(this, OnBoardingFirstActivity::class.java).apply {
            putExtra("email", email)
            putExtra("photoUrl", photoUrl)
            putExtra("displayName", displayName)
            putExtra("phone", phone)
        }
        startActivity(intent)
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
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
        binding.BTNext.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
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
        binding.BTNext.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
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
        binding.BTNext.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
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
        MainRouter().launch(this)
        finish()
    }

    private fun insertUser(token: String) {
        val newUser = UserModel(0, displayName.toString(), UIUtil.getExperience(userExperience, this), phone.toString(), email.toString(), 0.0, 0, 0, photoUrl.toString(), token)
        onBoardingSecondViewModel.saveUser(newUser)
    }
}