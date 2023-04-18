package com.example.glartekchallenge.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.glartekchallenge.ui.utils.ImageComponent

@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel) {
    val imageModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .width(180.dp)
    val movie = detailsViewModel.state.collectAsState().value.movie
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        ImageComponent(modifier = imageModifier, movie = movie)
        Text(text = movie.title, fontWeight = Bold)
        Text(text = movie.genre)
        Text(text = movie.rating)
        Text(text = movie.year.toString())
        Text(text = movie.plot)
        Text(text = movie.director)
        Text(text = movie.actors)
    }
}
