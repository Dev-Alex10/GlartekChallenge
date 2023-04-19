package com.example.glartekchallenge.ui.favorite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.ui.home.MovieCard

@Composable
fun FavoriteScreen(
    viewModel: FavoriteMovieViewModel = hiltViewModel(),
    onInfoClick: (Movie) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    LazyColumn(content = {
        items(state.favoriteList) { movie ->
            MovieCard(
                movie = movie,
                onInfoClick = onInfoClick,
                onFavoriteClick = { viewModel.onFavoriteClick(movie) },
                isFavorite = { viewModel.isFavorite(movie) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    })
}
