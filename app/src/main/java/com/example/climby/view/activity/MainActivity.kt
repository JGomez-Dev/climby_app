package com.example.climby.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.climby.R
import com.example.climby.data.model.user.UserModel
import com.example.climby.databinding.ActivityOnboardingSecondBinding
import com.example.climby.utils.IOnBackPressed
import com.example.climby.view.viewmodel.MainViewModel
import com.example.climby.view.viewmodel.OnBoardingSecondViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /*private lateinit var mainViewModel: MainViewModel*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)*/
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

       /* getData()*/


        /* navController.addOnDestinationChangedListener { _, destination, _ ->
             when (destination.label) {
                 "Mi perfil" -> navView.visibility = View.GONE
                 else -> navView.visibility = View.VISIBLE
             }

         }*/
       /* navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                "Publicar" -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }*/
    }

    /*private fun getData() {
        mainViewModel.getUser()
    }*/

    /*override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        (fragment as? IOnBackPressed)?.onBackPressed()?.let {
            super.onBackPressed()
        }
    }*/
}