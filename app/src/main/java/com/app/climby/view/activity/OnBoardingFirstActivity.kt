package com.app.climby.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.app.climby.R
import com.app.climby.databinding.ActivityOnboardingFirstBinding
import com.app.climby.view.viewmodel.OnBoardingFirstViewModel
import com.google.firebase.auth.FirebaseAuth
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
    private var phone: String? = null

    private lateinit var prefs: SharedPreferences.Editor

    private var openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                photoUrl = data?.data.toString()
                Glide.with(this).load(photoUrl).error(R.mipmap.user).into(binding.CIPhotoUser)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardingFirstViewModel = ViewModelProvider(this)[OnBoardingFirstViewModel::class.java]
        binding = ActivityOnboardingFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        getData()
        setPreferences()
        showKeyboard()

        Glide.with(this).load(photoUrl).error(R.mipmap.user).into(binding.root.findViewById<CircleImageView>(R.id.CIPhotoUser))
        binding.ETName.setText(displayName.toString())

        binding.BTSelectPhoto.setOnClickListener {
            openGallery()
        }
        binding.ETPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("ES"))
        binding.ETPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                TODO("")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onBoardingFirstViewModel.onUserPhoneTextChanged(s)
            }
        })
        binding.ETPhone.setText(phone)
        binding.ETPhone.text?.let { binding.ETPhone.setSelection(it.length) }
        binding.IVBack.setOnClickListener {

            onBackPressed()
        }

        onBoardingFirstViewModel.textLD.observe(this) {
            binding.BTContinue.isEnabled = it
            if (it) {
                binding.BTContinue.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
            } else {
                binding.BTContinue.setBackgroundColor(ContextCompat.getColor(this, R.color.disable))
            }
        }
        binding.BTContinue.setOnClickListener {
            prefs.putString("phone", binding.ETPhone.text.toString().replace(" ", ""))
            prefs.apply()
            showOnBoardingSecond()
        }



    }

    private fun showKeyboard() {
        binding.ETPhone.requestFocus()
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun closeKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    /*private fun checkPermissionSms() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1000)
        }
    }*/

    private fun getData() {
        val bundle = intent.extras
        email = bundle?.getString("email")
        provider = bundle?.getString("provider")
        photoUrl = bundle?.getString("photoUrl")
        displayName = bundle?.getString("displayName")
        phone = bundle?.getString("phone")
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
        closeKeyboard()
        startActivity(intent)
    }

    private fun showResumeTripActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        closeKeyboard()
        startActivity(intent)
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    override fun onBackPressed() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        showResumeTripActivity()
        finish()
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        openGallery.launch(gallery)
    }
}