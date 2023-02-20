package com.app.climby.view.activity.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.climby.view.activity.ui.HomeScreen
import com.google.firebase.auth.FirebaseUser
import com.jgomez.authentication_presentacion.navigation.authNavGraph
import com.jgomez.authentication_presentacion.navigation.onBoardingNavGraph
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
        startDestination = if(user != null) Graph.ONBOARDING else Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController, signIn, user)
        onBoardingNavGraph(navController = navController, user)
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}