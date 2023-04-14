package com.example.glartekchallenge

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.glartekchallenge.ui.home.HomeScreen
import com.example.glartekchallenge.ui.theme.GlartekChallengeTheme
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication @Inject constructor() : Application()

@Composable
fun MovieApp() {
    GlartekChallengeTheme {
        val navController = rememberNavController()
        Scaffold {
//            MovieNavHost(navController, Modifier.padding(it))
            HomeScreen(modifier = Modifier.padding(it))
        }
    }
}
