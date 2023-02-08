package com.app.climby.view.activity.ui.compose.screens.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.climby.view.activity.ui.compose.screens.AnimatedSplashScreen
import com.jgomez.authentication_presentacion.views.ui.LoginContent
import com.jgomez.authentication_presentacion.views.ui.OnBoardingContent

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = AuthScreen.Login.route) {
            LoginContent(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.OnBoarding1.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.OnBoarding2.route)
                }
            )
        }
        composable(route = AuthScreen.OnBoarding1.route) {
            OnBoardingContent(name = AuthScreen.OnBoarding1.route) {}
        }
        composable(route = AuthScreen.OnBoarding2.route) {
            OnBoardingContent(name = AuthScreen.OnBoarding2.route) {}
        }
    }
}
sealed class AuthScreen(val route: String){
    object Splash : AuthScreen(route = "SPLASH")
    object Login : AuthScreen(route = "LOGIN")
    object OnBoarding1 : AuthScreen(route = "ON_BOARDING_1")
    object OnBoarding2 : AuthScreen(route = "ON_BOARDING_2")
}
