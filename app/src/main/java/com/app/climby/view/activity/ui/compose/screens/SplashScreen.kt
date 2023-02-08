package com.app.climby.view.activity.ui.compose.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.climby.R
import com.app.climby.view.activity.ui.compose.screens.graph.AuthScreen
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        navController.navigate(AuthScreen.Login.route)
    }
    Splash()
}

@Composable
fun Splash() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val painterSplashScreen = painterResource(id = R.drawable.splash_screen)
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterSplashScreen,
            contentDescription = "Splash Image Background"
        )
        val painterSplashScreenClimby = painterResource(id = R.drawable.splash_climby)
        val painterSplashScreenLogo = painterResource(id = R.drawable.splash_logo)
        Row(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 65.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(8.dp),
                painter = painterSplashScreenClimby,
                contentDescription = "Splash Image Text"
            )
            Image(
                painter = painterSplashScreenLogo,
                contentDescription = "Splash Image Logo"
            )
        }

    }
}

@Composable
@Preview
fun SplashScreenView() {
    Splash()
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkView() {
    Splash()
}