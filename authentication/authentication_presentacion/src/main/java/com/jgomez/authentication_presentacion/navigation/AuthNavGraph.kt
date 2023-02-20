package com.jgomez.authentication_presentacion.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.jgomez.authentication_presentacion.views.ui.AnimatedSplashScreen
import com.jgomez.authentication_presentacion.views.ui.LoginContent
import com.jgomez.authentication_presentacion.views.ui.OnBoardingOneContent
import com.jgomez.authentication_presentacion.views.ui.OnBoardingTwoContent
import com.jgomez.common_utils.navigation.Graph

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    signIn: () -> Unit,
    user: FirebaseUser?
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Splash.route) {
            AnimatedSplashScreen(navController, user)
        }
        composable(route = AuthScreen.Login.route) {
            LoginContent(
                onGoogleClick = {
                    signIn()
                },
                onFacebookClick = {
                    //navController.popBackStack()
                    //navController.navigate(Graph.HOME)
                    //navController.navigate(AuthScreen.OnBoardingTwo.route)
                }
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Splash : AuthScreen(route = "SPLASH")
    object Login : AuthScreen(route = "LOGIN")
}
