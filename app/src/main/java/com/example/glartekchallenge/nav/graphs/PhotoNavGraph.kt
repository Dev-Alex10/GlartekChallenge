package com.example.glartekchallenge.nav.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.glartekchallenge.nav.destinations.Details
import com.example.glartekchallenge.nav.destinations.Favorite
import com.example.glartekchallenge.nav.destinations.Home
import com.example.glartekchallenge.ui.details.DetailsScreen
import com.example.glartekchallenge.ui.favorite.FavoriteScreen
import com.example.glartekchallenge.ui.home.HomeScreen

@Composable
fun MovieNavHost(navController: NavHostController, modifier: Modifier) {
    val uri = "https://movie.com"

    NavHost(navController = navController, startDestination = Home.route, modifier = modifier) {
        composable(route = Home.route) {
            HomeScreen(onInfoClick = { movie ->
                navController.navigateSingleTopTo(route = Details.route(movie.id))
            })
        }
        composable(
            route = Details.routeWithArgs,
            arguments = Details.arguments,
            deepLinks = listOf(
                navDeepLink { uriPattern = "$uri/" + Details.routeWithArgs }
            )
        ) {
            DetailsScreen(detailsViewModel = hiltViewModel())
        }
        composable(route = Favorite.route) {
            FavoriteScreen(onInfoClick = { movie ->
                navController.navigateSingleTopTo(route = Details.route(movie.id))
            })
        }

    }

}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
