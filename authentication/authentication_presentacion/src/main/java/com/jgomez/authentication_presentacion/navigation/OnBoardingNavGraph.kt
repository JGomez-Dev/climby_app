package com.jgomez.authentication_presentacion.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.jgomez.authentication_presentacion.views.ui.AnimatedSplashScreen
import com.jgomez.authentication_presentacion.views.ui.OnBoardingOneContent
import com.jgomez.authentication_presentacion.views.ui.OnBoardingTwoContent
import com.jgomez.common_utils.navigation.Graph

fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavHostController,
    user: FirebaseUser?
) {
    navigation(
        route = Graph.ONBOARDING,
        startDestination = OnBoardingScreen.OnBoardingTwo.route
    ) {
        composable(
            route = "${OnBoardingScreen.OnBoardingOne.route}/{photoUrl}/{name}",
            arguments = listOf(
                navArgument("photoUrl") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->

            val photoUrl = backStackEntry.arguments?.getString("photoUrl")
            val name = backStackEntry.arguments?.getString("name")

            requireNotNull(photoUrl)
            requireNotNull(name)

            OnBoardingOneContent(
                onClickBack = {
                    navController.navigate(AuthScreen.Login.route)
                },
                photoUrl = photoUrl,
                name = name
            )
        }
        composable(route = OnBoardingScreen.OnBoardingTwo.route) {
            if (user != null)
                OnBoardingTwoContent(
                    onClick = { navController.popBackStack() },
                    photoUrl = user.photoUrl.toString(),
                    name = user.displayName.toString()
                )
        }
    }
}


sealed class OnBoardingScreen(val route: String) {
    object OnBoardingOne : AuthScreen(route = "ON_BOARDING_1")
    object OnBoardingTwo : AuthScreen(route = "ON_BOARDING_2")
}

