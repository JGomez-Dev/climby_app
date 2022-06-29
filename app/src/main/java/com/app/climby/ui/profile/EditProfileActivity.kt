package com.app.climby.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ActivityEditProfileBinding
import com.app.climby.ui.profile.viewmodel.EditProfileViewModel
import com.app.climby.util.Commons
import com.app.climby.util.UserExperience
import com.app.climby.view.activity.AuthActivity
import com.app.climby.view.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel
    private var photoUrl: String? = null
    private var userExperience: String = "Principiante"
    private var userSession: UserModel = Commons.userSession!!

    /*private var storage = Firebase.storage*/
    private var uriPhoto: Uri? = null
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private lateinit var prefs: SharedPreferences.Editor

    private var openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                photoUrl = data?.data.toString()
                uriPhoto = data?.data!!
                Glide.with(this).load(photoUrl).error(R.mipmap.user).into(binding.CIPhotoUser)
                prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
                prefs.putString("photoUrl", photoUrl)
                prefs.apply()
                userSession.photo = photoUrl
                editProfileViewModel.updateUser(UserModel(userSession.id, userSession.name, getExperince(userExperience), binding.ETPhone.text.toString().replace(" ", ""), userSession.email, userSession.score, userSession.ratings, userSession.outings, photoUrl, userSession.token))
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editProfileViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
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
            updateUser(uriPhoto)
            showMainActivity()
        }
        binding.IVBack.setOnClickListener {
            onBackPressed()
        }

        binding.BTLogout.setOnClickListener {
            authActivity()
        }
        binding.root.findViewById<EditText>(R.id.ETPhone).addTextChangedListener(PhoneNumberFormattingTextWatcher("ES"))
        binding.root.findViewById<EditText>(R.id.ETPhone).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO algo despues del cambio del texto
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO algo despues del cambio del texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editProfileViewModel.onUsernameTextChanged(s)
            }
        })
        editProfileViewModel.textLD.observe(this) {
            binding.root.findViewById<Button>(R.id.BTSave).isEnabled = it
        }

        /*  editProfileViewModel.isComplete.observe(this, Observer {
              showMainActivity()
          })*/
        init()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun authActivity() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.clear()
        prefs.putString("email", userSession.email)
        prefs.putInt("id", userSession.id)
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        showResumeTripActivity()

    }

    private fun showResumeTripActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMainActivity() {
        val intent = Intent(applicationContext.applicationContext, MainActivity::class.java).apply {
            putExtra("exprienceProfile", userExperience)
        }
        startActivity(intent)
        overridePendingTransition(0, R.anim.slide_in_down)
    }

    private fun updateUser(data: Uri?) {
        if (data != null) {
            val filePath: StorageReference = storageRef.child("users/" + Commons.userSession?.phone)
            val uploadTask = filePath.putFile(data)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    editProfileViewModel.updateUser(UserModel(userSession.id, userSession.name, getExperince(userExperience), binding.ETPhone.text.toString().replace(" ", ""), userSession.email, userSession.score, userSession.ratings, userSession.outings, downloadUri, userSession.token))
                } else {
                    Toast.makeText(applicationContext, R.string.problem_with_image, Toast.LENGTH_LONG).show()
                }
            }
        }else{
            val re = Regex("[^0-9]")
            val tlf = re.replace( binding.ETPhone.text.toString(), "")// works
            if(tlf.length != 9)
                Toast.makeText(applicationContext, R.string.dont_valid, Toast.LENGTH_LONG).show()
            else
                editProfileViewModel.updateUser(UserModel(userSession.id, userSession.name, getExperince(userExperience), tlf.toString(), userSession.email, userSession.score, userSession.ratings, userSession.outings, userSession.photo, userSession.token))
        }
        Commons.userSession?.experience = getExperince(userExperience)
    }

    private fun init() {
        binding.ETName.setText(userSession.name)
        binding.ETPhone.setText(getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE)?.getString("phone", "..."))
        Glide.with(this).load(getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE)?.getString("photoUrl", "...")).error(R.mipmap.user).into(binding.CIPhotoUser)
        checkExperience()
    }

    private fun getExperince(userExperience: String): String {
        when (userExperience) {
            UserExperience.BEGINNER.status -> return "Principiante"
            UserExperience.MEDIUM.status -> return "Intermedio"
            UserExperience.ADVANCED.status -> return "Experimentado"
        }
        return "Principiante"
    }

    private fun checkExperience() {
        when (getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE)?.getString("experience", "...")) {
            "Principiante" -> selectedBeginner()
            "Intermedio" -> selectedIntermediate()
            "Experimentado" -> selectedExperienced()
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