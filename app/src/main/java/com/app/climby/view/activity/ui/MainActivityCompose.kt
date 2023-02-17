package com.app.climby.view.activity.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.app.climby.view.activity.ui.graph.RootNavigationGraph
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.jgomez.common_utils.ui.theme.ClimbyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signInIntent = Intent(this, SignInActivityCompose::class.java)
        startActivity(signInIntent)
    }
}
