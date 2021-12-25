package com.example.climby.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.climby.R
import com.example.climby.ui.profile.ProfileFragment
import com.example.climby.ui.publish.PublishFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

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

        val bundle = intent.extras
        if (bundle != null) {
            val school =  bundle.getString("schoolPublish", "")
            val province = bundle.getInt("provincePublish", 0)
            val type = bundle.getInt("typePublish", 0)
            val date = bundle.getString("datePublish", "")
            val dateFormat = bundle.getString("datePublishWithOutFormat", "")
            val places = bundle.getInt("placePublish", 0)

            /*val profile = bundle.getBoolean("profile", false)*/

            if(school != ""){
                val f = PublishFragment()
                f.arguments = bundle
                val fm = supportFragmentManager
                fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
            }/*else if(profile){
                val f = ProfileFragment()
                f.arguments = bundle
                val fm = supportFragmentManager
                fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
            }*/
        }
    }
}