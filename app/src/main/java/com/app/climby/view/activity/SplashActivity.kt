package com.app.climby.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.app.climby.R
import com.app.climby.databinding.SplashScreenBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var screenBinding: SplashScreenBinding

    private lateinit var topAnim : Animation
    private lateinit var bottomAnim : Animation
    private var SPLASH_SCREEN: Int = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(screenBinding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN )

        //topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)


        //screenBinding.IVBackground.animation = topAnim
        screenBinding.IVBackground.animation = bottomAnim

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val authActivity = Intent(application, AuthActivity::class.java)
                startActivity(authActivity)
                overridePendingTransition(0,R.anim.anim_auth)
                finish()
            },700)

       /* try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val authActivity = Intent(application, AuthActivity::class.java)
        startActivity(authActivity)
        finish()*/

    }
}
