package com.example.climby.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.climby.R
import com.example.climby.ui.profile.ProfileFragment
import com.example.climby.ui.publish.PublishFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView
    private var TAG: String = "PushNotifiacion"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_publish) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.i(TAG, token)
            /*Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()*/
        })


        val bundle = intent.extras
        if (bundle != null) {
            val school =  bundle.getString("schoolPublish", "")
            val exprience = bundle.getString("exprienceProfile", null)
            val province = bundle.getInt("provincePublish", 0)
            val type = bundle.getInt("typePublish", 0)
            val date = bundle.getString("datePublish", "")
            val dateFormat = bundle.getString("datePublishWithOutFormat", "")
            val places = bundle.getInt("placePublish", 0)
            val to =  bundle.getString("to", "")

            /*val profile = bundle.getBoolean("profile", false)*/
            if(to != ""){
                if(to == "myOutigsFragment"){
                    val f = ProfileFragment()
                    f.arguments = bundle
                    val fm = supportFragmentManager
                    fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
                }
            }

            if(school != ""){
                val f = PublishFragment()
                f.arguments = bundle
                val fm = supportFragmentManager
                fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
            }else if(!exprience.isNullOrEmpty()){
                navView.selectedItemId = R.id.navigation_profile
               /* val f = ProfileFragment()
                f.arguments = bundle
                val fm = supportFragmentManager
                fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()*/
            }
        }
    }
}