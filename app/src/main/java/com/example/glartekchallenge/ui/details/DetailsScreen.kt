package com.example.glartekchallenge.ui.details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.glartekchallenge.R
import com.example.glartekchallenge.ui.utils.ImageComponent

@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel) {
    val imageModifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .width(300.dp)
    val movie = detailsViewModel.state.collectAsState().value.movie
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        item { ImageComponent(modifier = imageModifier, movie = movie) }
        item {
            Text(text = movie.title, fontWeight = Bold)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.genre, movie.genre))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.rating, movie.rating))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.year, movie.year.toString()))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.plot, movie.plot))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.director, movie.director))
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = stringResource(id = R.string.actors, movie.actors))
        }
    }
}
