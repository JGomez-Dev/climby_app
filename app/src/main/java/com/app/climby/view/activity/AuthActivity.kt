package com.app.climby.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.ActivityAuthBinding
import com.app.climby.ui.discover.router.TripUsersRouter
import com.app.climby.ui.profile.router.RequestsRouter
import com.app.climby.ui.profile.router.ResumeTripRouter
import com.app.climby.util.Commons
import com.app.climby.util.From
import com.app.climby.view.router.MainRouter
import com.app.climby.view.router.OnBoardingFirstRouter
import com.app.climby.view.router.OnBoardingSecondRouter
import com.app.climby.view.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityAuthBinding

    private var googleSignIn =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            binding.CLLoading.isVisible = true
            binding.BTGoogle.isClickable = false
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    authViewModel.getUserByEmail(account.email ?: "")
                                    authViewModel.exists.observe(this, Observer {
                                        if (it)
                                            goToMainActivity()
                                        else {
                                            binding.CLLoading.isVisible = false
                                            goToOnBoardingFirst(account.email ?: "", account.photoUrl?.toString(), account.displayName)
                                        }
                                    })
                                } else {
                                    showAlert()
                                }
                            }
                    }
                } catch (e: ApiException) {
                    showAlert()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session()
        binding.BTGoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            val signInIntent = googleClient.signInIntent
            googleSignIn.launch(signInIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.CLAuthentication.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val id = prefs.getInt("id", 0)
        val photoUrl = prefs.getString("photoUrl", null)
        val displayName = prefs.getString("displayName", null)
        val phone = prefs.getString("phone", null)
        val experience = prefs.getString("experience", null)
        val outings = prefs.getInt("outputs", 0)
        val score = prefs.getFloat("score", 0f)
        val ratings = prefs.getInt("ratings", 0)
        val token = prefs.getString("token", null)

        if (!email.isNullOrEmpty() && !photoUrl.isNullOrEmpty() && !displayName.isNullOrEmpty()) {
            if (!experience.isNullOrEmpty()) {
                Commons.userSession = UserModel(id, displayName, experience, phone, email, score.toDouble(), ratings, outings, photoUrl, token)
                binding.CLAuthentication.visibility = View.INVISIBLE

                val i = intent
                val extras = i.extras
                val push = extras?.getString("push")
                val to = extras?.getString("to")
                val tripId = extras?.getString("id")
                if (extras != null && push != null && to != null) {
                    when (to) {
                        "RequestsActivity" -> goToDetalleRequest(tripId)
                        "TripUsersActivity" -> goToTripUser(tripId)
                        "ProfileFragment" -> goToProfile()
                        "ResumeTripActivity" -> goToResumeTrip(tripId)
                        else -> {
                            binding.CLLoading.isVisible = false
                            goToMainActivity()
                        }
                    }
                } else {
                    binding.CLLoading.isVisible = false
                    goToMainActivity()
                }
            } else if (!phone.isNullOrEmpty()) {
                binding.CLAuthentication.visibility = View.INVISIBLE
                goToOnBoardingSecond(email, photoUrl, displayName, phone)
            } else {
                binding.CLAuthentication.visibility = View.INVISIBLE
                binding.CLLoading.isVisible = false
                goToOnBoardingFirst(email, photoUrl, displayName)
            }
        }
    }

    private fun goToResumeTrip(tripId: String?) {
        authViewModel.getTripById(tripId!!.toInt())
        authViewModel.tripModel.observe(this, Observer {
            ResumeTripRouter().launch(this, it)
            finish()
        })
    }

    private fun goToProfile() {
        MainRouter().launch(this, null, From.PROFILE, isEdit = false)
        finish()
    }

    private fun goToDetalleRequest(tripId: String?) {
        authViewModel.getTripById(tripId!!.toInt())
        authViewModel.tripModel.observe(this, Observer {
            RequestsRouter().launch(this, it, From.PROFILE)
            finish()
        })
    }

    private fun goToTripUser(tripId: String?) {
        authViewModel.getTripById(tripId!!.toInt())
        authViewModel.tripModel.observe(this, Observer {
            TripUsersRouter().launch(this, it, From.PROFILE)
            finish()
        })
    }

    private fun goToOnBoardingFirst(email: String?, photoUrl: String?, displayName: String?) {
        OnBoardingFirstRouter().launch(this, email, photoUrl, displayName, null)
        finish()
    }

    private fun goToOnBoardingSecond(email: String, photoUrl: String?, displayName: String?, phone: String?) {
        OnBoardingSecondRouter().launch(this, email, photoUrl, displayName, phone, true)
        finish()
    }

    private fun goToMainActivity() {
        MainRouter().launch(this)
        finish()
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
