package com.app.climby.view.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.app.climby.R
import com.app.climby.databinding.SplashScreenBinding
import com.app.climby.view.router.AuthRouter

class SplashActivity : AppCompatActivity() {

    private lateinit var screenBinding: SplashScreenBinding
    private var SPLASH_SCREEN: Long = 700

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screenBinding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(screenBinding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        screenBinding.IVBackground.animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                goToAuthActivity()
            }, SPLASH_SCREEN)
    }

    private fun goToAuthActivity() {
        AuthRouter().launch(this)
        finish()
    }
}
