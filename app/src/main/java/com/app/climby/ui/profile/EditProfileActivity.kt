package com.app.climby.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ActivityEditProfileBinding
import com.app.climby.ui.profile.viewmodel.EditProfileViewModel
import com.app.climby.util.Commons
import com.app.climby.util.From
import com.app.climby.util.UIUtil
import com.app.climby.util.UserExperience
import com.app.climby.view.router.AuthRouter
import com.app.climby.view.router.MainRouter
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel
    private var photoUrl: String? = null
    private lateinit var userExperience: String
    private var userSession: UserModel = Commons.userSession!!

    private var uriPhoto: Uri? = null

    private var openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                photoUrl = data?.data.toString()
                uriPhoto = data?.data!!
                Glide.with(this).load(photoUrl).error(R.mipmap.user).into(binding.CIPhotoUser)
                /*prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
                prefs.putString("photoUrl", photoUrl)
                prefs.apply()
                userSession.photo = photoUrl*/
                //editProfileViewModel.updateUser(UserModel(userSession.id, userSession.name, getExperince(userExperience), binding.ETPhone.text.toString().replace(" ", ""), userSession.email, userSession.score, userSession.ratings, userSession.outings, photoUrl, userSession.token))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editProfileViewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BTSelectPhotoEdit.setOnClickListener {
            openGallery()
        }
        binding.CVBeginner.setOnClickListener {
            selectedBeginner()
        }
        binding.CVIntermediate.setOnClickListener {
            selectedIntermediate()
        }
        binding.CVExperienced.setOnClickListener {
            selectedExperienced()
        }
        binding.BTSave.setOnClickListener {
            if(uriPhoto != null)
                editProfileViewModel.uploadImage(uriPhoto)
            else {
                val re = Regex("[^0-9]")
                val tlf = re.replace(binding.ETPhone.text.toString(), "")
                if (tlf.length != 9)
                    Toast.makeText(applicationContext, R.string.dont_valid, Toast.LENGTH_LONG).show()
                else {
                    updateUser()
                }

            }

        }
        binding.IVBack.setOnClickListener {
            onBackPressed()
        }
        binding.BTLogout.setOnClickListener {
            clearSharedPreference()
            goToAuthActivity()
        }
        binding.ETPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("ES"))
        binding.ETPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editProfileViewModel.onUsernameTextChanged(s)
            }
        })
        editProfileViewModel.textLD.observe(this) {
            binding.BTSave.isEnabled = it
        }
        editProfileViewModel.isUploadImage.observe(this) {
            if (it) {
                updateUser()
            } else
                Toast.makeText(applicationContext, R.string.problem_with_image, Toast.LENGTH_LONG).show()
        }
        editProfileViewModel.isComplete.observe(this) {
            if (it)
                goToMainActivity()
        }

        init()
    }

    private fun updateUser() {
        val user = UserModel(userSession.id, binding.ETName.text.toString(), UIUtil.getExperience(userExperience, this), binding.ETPhone.text.toString().replace(" ", ""), userSession.email, userSession.score, userSession.ratings, userSession.outings, if (editProfileViewModel.uriImage.value != null) editProfileViewModel.uriImage.value else userSession.photo, userSession.token)
        editProfileViewModel.updateUser(user)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun clearSharedPreference() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.clear()
        prefs.putString("email", userSession.email)
        prefs.putInt("id", userSession.id)
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
    }

    private fun goToAuthActivity() {
        AuthRouter().launch(this)
        finish()
    }

    private fun goToMainActivity() {
        MainRouter().launch(this, null, From.PROFILE, isEdit = false)
        finish()
    }

    private fun init() {
        binding.ETName.setText(userSession.name)
        binding.ETPhone.setText(userSession.phone)
        Glide.with(this).load(userSession.photo).error(R.mipmap.user).into(binding.CIPhotoUser)
        checkExperience()
        userExperience = getString(R.string.edit_profile_beginner)
    }

    private fun checkExperience() {
        when (getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE)?.getString("experience", "...")) {
            getString(R.string.edit_profile_beginner) -> selectedBeginner()
            getString(R.string.edit_profile_intermediate) -> selectedIntermediate()
            getString(R.string.edit_profile_experienced) -> selectedExperienced()
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        openGallery.launch(gallery)
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
        binding.BTSave.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
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
        binding.BTSave.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
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
        binding.BTSave.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
        userExperience = UserExperience.BEGINNER.status
    }
}