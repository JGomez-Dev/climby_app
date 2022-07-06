package com.app.climby.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.climby.R
import com.app.climby.databinding.ActivityMainBinding
import com.app.climby.ui.publish.PublishFragment
import com.app.climby.util.Commons
import com.app.climby.util.UIUtil
import com.app.climby.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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
            mainViewModel.getNotification(Commons.userSession?.id!!)
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
    }
}