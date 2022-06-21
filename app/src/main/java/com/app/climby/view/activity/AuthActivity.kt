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
import com.app.climby.ui.discover.TripUsersActivity
import com.app.climby.ui.profile.RequestsActivity
import com.app.climby.ui.profile.ResumeTripActivity
import com.app.climby.utils.Commons
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
                                    //val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                                    //val email = prefs.getString("email", null)
                                    authViewModel.getUserByEmail(account.email ?: "")
                                    authViewModel.exists.observe(this, Observer { it ->
                                        if (it)
                                            showMainActivity()
                                        else
                                            showOnBoardingFirst(account.email ?: "", account.photoUrl?.toString(), account.displayName)
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

        /*authViewModel.isCharget.observe(this , {
            if(it){
                showMainActivity()
            }
        })*/
    }

    override fun onStart() {
        super.onStart()
        binding.CLAuthentication.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        /*prefs.edit().clear().apply()*/
        val email = prefs.getString("email", null)
        val id = prefs.getInt("id", 0)
        //val provider = prefs.getString("provider", null)
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

                if (extras != null) {
                    val push = extras.getString("push")
                    if (push != null) {
                        val to = extras.getString("to")
                        if (to != null) {
                            val id = extras.getString("id")
                            when (to) {
                                "RequestsActivity" -> goToDetalleRequest(id)
                                "TripUsersActivity" -> goToDetalleTripUsers(id)
                                "ProfileFragment" -> goToProfile()
                                "ResumeTripActivity" -> goToResumeTrip(id)
                                else -> {
                                    showMainActivity()
                                }
                            }
                        }
                    } else {
                        showMainActivity()
                    }
                } else {
                    showMainActivity()
                }
            } else if (!phone.isNullOrEmpty()) {
                binding.CLAuthentication.visibility = View.INVISIBLE
                showOnBoardingSecond(email, photoUrl, displayName, phone)
            } else {
                binding.CLAuthentication.visibility = View.INVISIBLE
                showOnBoardingFirst(email, photoUrl, displayName)
            }
        }
    }

    private fun goToResumeTrip(idTrip: String?) {
        val intent = Intent(applicationContext.applicationContext, ResumeTripActivity::class.java).apply {
            putExtra("from", "profile")
            putExtra("idTrip", idTrip?.toInt())
        }
        startActivity(intent)
        finish()
    }

    private fun goToProfile() {
        val intent = Intent(applicationContext.applicationContext, MainActivity::class.java).apply {
            putExtra("from", "profile")
            putExtra("to", "notification")
            putExtra("viewPager", 1)
        }
        startActivity(intent)
        finish()
    }

    private fun goToDetalleRequest(idTrip: String?) {
        val intent = Intent(applicationContext.applicationContext, RequestsActivity::class.java).apply {
            putExtra("from", "profile")
            putExtra("idTrip", idTrip?.toInt())
        }
        startActivity(intent)
        finish()
    }

    private fun goToDetalleTripUsers(idTrip: String?) {
        val intent = Intent(applicationContext.applicationContext, TripUsersActivity::class.java).apply {
            putExtra("from", "profile")
            putExtra("idTrip", idTrip?.toInt())
        }
        startActivity(intent)
        finish()
    }

    /*private fun getData(email: String){
        return authViewModel.getUserByEmail(email)
    }*/

    private fun showOnBoardingFirst(email: String, photoUrl: String?, displayName: String?) {
        val intent = Intent(this, OnBoardingFirstActivity::class.java).apply {
            putExtra("email", email)
            putExtra("photoUrl", photoUrl)
            //putExtra("provider", provider.name)
            putExtra("displayName", displayName)
        }
        binding.CLLoading.isVisible = false
        startActivity(intent)
        finish()
    }

    private fun showOnBoardingSecond(email: String, photoUrl: String?, displayName: String?, phone: String?) {
        val intent = Intent(this, OnBoardingSecondActivity::class.java).apply {
            putExtra("email", email)
            putExtra("photoUrl", photoUrl)
            //putExtra("provider", provider.name)
            putExtra("displayName", displayName)
            putExtra("phone", phone)
        }
        startActivity(intent)
        finish()
    }

    private fun showMainActivity(/*email: String, photoUrl: String?, displayName: String?, provider: ProviderType, phone: String?, experience: String?*/) {
        val intent = Intent(this, MainActivity::class.java)
        binding.CLLoading.isVisible = false
        startActivity(intent)
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
