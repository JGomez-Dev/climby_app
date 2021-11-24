package com.example.climby.view.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.climby.R
import com.example.climby.databinding.ActivityOnboardingFirstBinding
import com.example.climby.view.viewmodel.OnBoardingFirstViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView


@AndroidEntryPoint
class OnBoardingFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingFirstBinding
    private lateinit var onBoardingFirstViewModel: OnBoardingFirstViewModel
    private var email: String? = null
    private var provider: String? = null
    private var photoUrl: String? = null
    private var displayName: String? = null
    private lateinit var prefs: SharedPreferences.Editor
    private var openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                photoUrl = data?.data.toString()
                /*prefs.putString("photoUrl", photoUrl)*/
                Glide.with(this).load(photoUrl).error(R.mipmap.user).into(binding.CIPhotoUser)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingFirstViewModel = ViewModelProvider(this).get(OnBoardingFirstViewModel::class.java)
        binding = ActivityOnboardingFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setPreferences()

        Glide.with(this).load(photoUrl).error(R.mipmap.user).into(binding.root.findViewById<CircleImageView>(R.id.CIPhotoUser))
        binding.ETName.setText(displayName.toString())
        binding.BTSelectPhoto.setOnClickListener {
            openGallery()
        }
        binding.ETPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.ETPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onBoardingFirstViewModel.onUsernameTextChanged(s)
            }
        })
        onBoardingFirstViewModel.textLD.observe(this, {
            binding.BTContinue.isEnabled = it
        })
        binding.BTContinue.setOnClickListener {
            prefs.putString("phone", binding.ETPhone.text.toString().replace(" ", ""))
            prefs.apply()
            showOnBoardingSecond()
        }
    }

    private fun getData() {
        val bundle = intent.extras
        email = bundle?.getString("email")
        provider = bundle?.getString("provider")
        photoUrl = bundle?.getString("photoUrl")
        displayName = bundle?.getString("displayName")
    }

    private fun setPreferences() {
        prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.putString("photoUrl", photoUrl)
        prefs.putString("displayName", displayName)
        prefs.apply()
    }

    private fun showOnBoardingSecond() {
        val intent = Intent(this, OnBoardingSecondActivity::class.java).apply {
            putExtra("email", email)
            putExtra("photoUrl", photoUrl)
            putExtra("provider", provider)
            putExtra("displayName", displayName)
            putExtra("phone", binding.ETPhone.text.toString().replace(" ", ""))
        }
        startActivity(intent)
        finish()
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        openGallery.launch(gallery)
    }
}