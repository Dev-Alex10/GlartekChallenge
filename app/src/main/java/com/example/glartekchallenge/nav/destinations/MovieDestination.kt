package com.example.glartekchallenge.nav.destinations

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface MovieDestination {
    val route: String
    val name: String
}

object Home : MovieDestination {
    override val route = "home"
    override val name = "Movie List"
}

object Details : MovieDestination {
    override val route = "details"
    override val name = "Movie Details"
    private const val movieId = "movieId"
    val routeWithArgs = "$route?movieId={$movieId}"
    val arguments = listOf(
        navArgument(movieId) {//arguments for additional safety
            type = NavType.StringType
        })

    fun route(movieId: String): String {
        return "$route?movieId=$movieId"
    }
}
