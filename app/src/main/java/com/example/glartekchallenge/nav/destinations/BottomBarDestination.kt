package com.example.glartekchallenge.nav.destinations

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector

interface BottomBarDestination {
    val route: String
    val icon: ImageVector
    val name: String
}

object Favorite : BottomBarDestination {
    override val route = "favorite"
    override val icon = androidx.compose.material.icons.Icons.Filled.Favorite
    override val name = "Favorites"
}
