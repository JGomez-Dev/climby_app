package com.jgomez.authentication_presentacion.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.jgomez.authentication_presentacion.views.ui.AnimatedSplashScreen
import com.jgomez.authentication_presentacion.views.ui.LoginContent
import com.jgomez.authentication_presentacion.views.ui.OnBoardingOneContent
import com.jgomez.authentication_presentacion.views.ui.OnBoardingTwoContent
import com.jgomez.common_utils.navigation.Graph
import com.jgomez.common_utils.ui.utils.encodeUrl

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
        composable(
            route = "${AuthScreen.OnBoardingOne.route}/{photoUrl}/{name}/{email}",
            arguments = listOf(
                navArgument("photoUrl") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->

            val photoUrl = backStackEntry.arguments?.getString("photoUrl")
            val name = backStackEntry.arguments?.getString("name")
            val email = backStackEntry.arguments?.getString("email")

            requireNotNull(photoUrl)
            requireNotNull(name)
            requireNotNull(email)

            OnBoardingOneContent(
                photoUrl = photoUrl,
                name = name,
                onClickBack = { navController.popBackStack() },
                onClickButton = {
                    navController.navigate(
                        "${AuthScreen.OnBoardingTwo.route}/${photoUrl.toString().encodeUrl()}/${name}/${it}/${email}"
                    )
                }
            )
        }
        composable(
            route = "${AuthScreen.OnBoardingTwo.route}/{photoUrl}/{name}/{phone}/{email}",
            arguments = listOf(
                navArgument("photoUrl") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("phone") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->

            val photoUrl = backStackEntry.arguments?.getString("photoUrl")
            val name = backStackEntry.arguments?.getString("name")
            val phone = backStackEntry.arguments?.getString("phone")
            val email = backStackEntry.arguments?.getString("email")

            requireNotNull(photoUrl)
            requireNotNull(name)
            requireNotNull(phone)
            requireNotNull(email)

            OnBoardingTwoContent(
                onClickBack = { navController.popBackStack() },
                onClickButton = {
                    navController.navigate(
                        "${Graph.HOME}/${
                            photoUrl.toString().encodeUrl()
                        }/${name}/${email}/${phone}/${it}"
                    )
                }
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Splash : AuthScreen(route = "SPLASH")
    object Login : AuthScreen(route = "LOGIN")
    object OnBoardingOne : AuthScreen(route = "ON_BOARDING_1")
    object OnBoardingTwo : AuthScreen(route = "ON_BOARDING_2")
}
