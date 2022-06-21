package com.app.climby.view.activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.ui.profile.ProfileFragment
import com.app.climby.ui.publish.PublishFragment
import com.app.climby.utils.Commons
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView
    private var userSession: UserModel = Commons.userSession!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        navView.itemIconTintList = null;
        changeItemWiseTextProperties(navView.menu)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_publish) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        val bundle = intent.extras
        if (bundle != null) {
            val school = bundle.getString("schoolPublish", "")
            val exprience = bundle.getString("exprienceProfile", null)
            val province = bundle.getInt("provincePublish", 0)
            val type = bundle.getInt("typePublish", 0)
            val date = bundle.getString("datePublish", "")
            val dateFormat = bundle.getString("datePublishWithOutFormat", "")
            val places = bundle.getInt("placePublish", 0)
            /*val to = bundle.getString("to", "")*/


            val from = bundle.getString("from", null)
            val notification = bundle.getString("notification", null)

            /*if (to != "") {
                *//*TODO tengo que meter en los extras cuando venga del dialog que se tiene que marcar la navvar*//*
                if (to == "myOutigsFragment") {
                    val f = ProfileFragment()
                    f.arguments = bundle
                    val fm = supportFragmentManager
                    fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
                }
            }*/
            if (from == "profile") {
                if(notification == null){
                    navView.selectedItemId = R.id.navigation_profile
                }else{
                    bundle.getInt("viewPager", 0)
                    val f = ProfileFragment()
                    f.arguments = bundle
                    val fm = supportFragmentManager
                    fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
                }
            }
            if (school != "") {
                val f = PublishFragment()
                f.arguments = bundle
                val fm = supportFragmentManager
                fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()
            } else if (!exprience.isNullOrEmpty()) {
                navView.selectedItemId = R.id.navigation_profile
                /* val f = ProfileFragment()
                 f.arguments = bundle
                 val fm = supportFragmentManager
                 fm.beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit()*/
            }
        }

        /*navView.getOrCreateBadge(R.id.navigation_profile).apply {
            isVisible = true
            backgroundColor = (ContextCompat.getColor(applicationContext, R.color.primary))
        }*/

    }

    private fun changeItemWiseTextProperties(menu: Menu) {
        Glide.with(this)
            .asBitmap()
            .circleCrop()
            .load(userSession.photo)
            .error(R.drawable.ic_baseline_person_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>(100, 100) {
                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                    menu.getItem(2).icon = BitmapDrawable(resources, resource)
                }
            })
    }

    /*private fun goToDetalleRequest(idTrip: Int, siteTrip: String) {
        val intent = Intent(applicationContext.applicationContext, RequestsActivity::class.java).apply {
            putExtra("from", "profile")
            putExtra("idTrip", idTrip)
            putExtra("siteTrip", siteTrip)
        }
        startActivity(intent)
    }*/
}