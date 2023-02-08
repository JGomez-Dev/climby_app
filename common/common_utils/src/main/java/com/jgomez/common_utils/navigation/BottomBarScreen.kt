package com.jgomez.common_utils.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.jgomez.common_utils.ui.theme.ClimbyColor

const val DISCOVER = "Descubre"
const val PUBLISH = "Publicar"
const val PROFILE = "Perfil"

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
){
    object Discover : BottomBarScreen(
        route = DISCOVER,
        title = DISCOVER,
        icon = Icons.Default.Home,
    )
    object Publish : BottomBarScreen(
        route = PUBLISH,
        title = PUBLISH,
        icon = Icons.Default.Settings,
    )
    object Profile : BottomBarScreen(
        route = PROFILE,
        title = PROFILE,
        icon = Icons.Default.Person,
    )
}

