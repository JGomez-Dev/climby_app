package com.app.climby.view.activity.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.climby.view.activity.ui.HomeScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    user: FirebaseUser?,
    signIn: () -> Unit
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = if (user == null) Graph.AUTHENTICATION else Graph.HOME
    ) {
        authNavGraph(navController = navController, signIn)
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}