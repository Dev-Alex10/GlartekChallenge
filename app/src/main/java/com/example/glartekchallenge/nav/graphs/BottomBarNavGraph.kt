package com.example.glartekchallenge.nav.graphs

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.glartekchallenge.nav.destinations.Favorite

@Composable
fun BottomBarNavHost(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    BottomNavigation {
        val items = listOf(Favorite)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.route
                    )
                },
                label = { Text(screen.name) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigateSingleTopTo(screen.route)
                })
        }
    }
}
