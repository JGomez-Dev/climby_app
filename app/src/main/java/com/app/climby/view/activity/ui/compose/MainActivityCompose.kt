package com.app.climby.view.activity.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.app.climby.view.activity.ui.compose.screens.graph.RootNavigationGraph
import com.app.climby.view.activity.ui.theme.ClimbyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClimbyTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}
