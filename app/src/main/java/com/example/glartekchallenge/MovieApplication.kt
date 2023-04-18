package com.example.glartekchallenge

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.glartekchallenge.ui.theme.GlartekChallengeTheme
import com.example.glartekchallenge.ui.utils.MovieAppBar
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication @Inject constructor() : Application()

@Composable
fun MovieApp(
    navController: NavHostController = rememberNavController(),
) {
    val context: Context = LocalContext.current
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val currentScreenTitle = when (currentDestination?.route) {
        Home.route -> Home.name
        Details.routeWithArgs -> Details.name
        else -> {
            ""
        }
    }
    GlartekChallengeTheme {
        Scaffold(topBar = {
            MovieAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                currentScreenTitle = currentScreenTitle,
                navigateUp = { navController.navigateUp() },
                shareOnClick = {
                    shareDeepLink(
                        context,
                        Details.route(
                            navController.currentBackStackEntry?.arguments
                                ?.getString("movieId")
                                ?: ""
                        )
                    )
                },
            )
        }) {
            MovieNavHost(navController, Modifier.padding(it))
        }
    }
}

fun shareDeepLink(context: Context, route: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "https://movie.com/${route}")
    val shareIntent = Intent.createChooser(intent, "Choose one")
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(shareIntent)
}
