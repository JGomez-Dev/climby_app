package com.app.climby.view.activity.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.climby.view.activity.ui.HomeScreen
import com.google.firebase.auth.FirebaseUser
import com.jgomez.authentication_presentacion.navigation.authNavGraph
import com.jgomez.common_utils.navigation.Graph

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    user: FirebaseUser?,
    signIn: () -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController, signIn, user)
        composable(
            route = "${Graph.HOME}/{photoUrl}/{name}/{phone}/{email}/{experience}",
            arguments = listOf(
                navArgument("photoUrl") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("phone") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("experience") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val photoUrl = backStackEntry.arguments?.getString("photoUrl")
            val name = backStackEntry.arguments?.getString("name")
            val phone = backStackEntry.arguments?.getString("phone")
            val email = backStackEntry.arguments?.getString("email")
            val experience = backStackEntry.arguments?.getString("experience")

            requireNotNull(photoUrl)
            requireNotNull(name)
            requireNotNull(phone)
            requireNotNull(email)
            requireNotNull(experience)

            HomeScreen(
                photoUrl = photoUrl,
                name = name,
                phone = phone,
                email = email,
                experience = experience
            )
        }
    }
}