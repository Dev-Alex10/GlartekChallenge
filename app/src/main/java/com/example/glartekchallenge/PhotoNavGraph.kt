package com.example.glartekchallenge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.ui.home.HomeScreen

@Composable
fun MovieNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Home.route, modifier = modifier) {
        composable(route = Home.route) {
            HomeScreen(onInfoClick = { movie ->
                navController.navigateToMovieDetails(movie = movie)
            },
                onFavoriteClick = {})
        }
        composable(route = Details.routeWithArgs, arguments = listOf(Details.arguments)) {
//            DetailsScreen(detailsViewModel = hiltViewModel())
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

fun NavHostController.navigateToMovieDetails(movie: Movie) {
    this.navigateSingleTopTo(Details.route(movie))
}

