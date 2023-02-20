package com.app.climby.view.activity.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jgomez.common_utils.navigation.BottomBarScreen
import com.jgomez.common_utils.navigation.Graph
import com.jgomez.discover_presentation.views.ui.CardDetailContent
import com.jgomez.discover_presentation.views.ui.DiscoverContent
import com.jgomez.discover_presentation.views.ui.ProvinceContent
import com.jgomez.profile_presentation.views.ui.ProfileContent
import com.jgomez.publish_presentation.views.ui.PublishContent

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Discover.route
    ) {
        composable(route = BottomBarScreen.Discover.route) {
            DiscoverContent()
        }
        composable(route = BottomBarScreen.Publish.route) {
            PublishContent(
                name = BottomBarScreen.Publish.route,
                onClick = {},
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileContent(
                name = BottomBarScreen.Profile.route,
                onClick = {},
            )
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Province.route
    ) {
        composable(route = DetailsScreen.Province.route) {
            ProvinceContent(name = DetailsScreen.Province.route) {
                navController.navigate(DetailsScreen.CardDetail.route)
            }
        }
        composable(route = DetailsScreen.CardDetail.route) {
            CardDetailContent(name = DetailsScreen.CardDetail.route) {
                navController.popBackStack()
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Province : DetailsScreen(route = "PROVINCE")
    object CardDetail : DetailsScreen(route = "CARD_DETAIL")
}