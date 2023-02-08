package com.app.climby.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.climby.R
import com.app.climby.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController

        navView.setupWithNavController(navHostFragment)
    }
}


/* private lateinit var binding: ActivityMainBinding
 private lateinit var mainViewModel: MainViewModel

 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)
     val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
     val navController = navHostFragment.navController
     binding.navView.setupWithNavController(navController)
     binding.navView.itemIconTintList = null;
     UIUtil.changeItemWiseTextProperties(binding.navView.menu, this)

     navController.addOnDestinationChangedListener { _, destination, _ ->
         if (destination.id == R.id.navigation_publish) {
             binding.navView.visibility = View.GONE
         } else {
             binding.navView.visibility = View.VISIBLE
         }
         mainViewModel.getNotification(Commons.userSession?.email!!)
     }

     mainViewModel.exitsNotification.observe(this, Observer {
         if (it != 0) {
             binding.navView.getOrCreateBadge(R.id.navigation_profile).apply {
                 isVisible = true
                 backgroundColor = (ContextCompat.getColor(applicationContext, R.color.primary))
             }
         }else
             binding.navView.getOrCreateBadge(R.id.navigation_profile).apply {
                 isVisible = false
             }
     })

     val bundle = intent.extras
     if (bundle != null) {
         val from = bundle.getString("from", null)
         if (from == "profile") {
             binding.navView.selectedItemId = R.id.navigation_profile
         }
         if (from == "publish") {
             val f = PublishFragment()
             f.arguments = bundle
             val fm = supportFragmentManager
             fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
         }
     }
 }*/