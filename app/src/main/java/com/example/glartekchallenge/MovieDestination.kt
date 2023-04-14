package com.example.glartekchallenge

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.glartekchallenge.data.domain.Movie

interface MovieDestination {
    val route: String
}

object Home : MovieDestination {
    override val route = "home"
}

object Details : MovieDestination {
    override val route = "details"
    private const val movieArg = "movie"
    val routeWithArgs = "${route}?movieArg={$movieArg}"
    val arguments = navArgument(movieArg) {//arguments for additional safety
        type = NavType.StringType
    }

    fun route(movie: Movie): String {
        return "$route?movieArg=${movie.id}"
    }
}
