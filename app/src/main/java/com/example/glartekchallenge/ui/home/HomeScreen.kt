package com.example.glartekchallenge.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.glartekchallenge.R
import com.example.glartekchallenge.data.domain.Movie

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onInfoClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    Column(modifier = modifier) {
        TextField(
            value = state.text,
            onValueChange = viewModel::onTextChanged,
            modifier = Modifier.padding(10.dp),
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
        )
        when (state.movieState) {
            is HomeViewModel.MoviesLoadingState.Error -> Text(
                text = state.movieState.t.toString()
            )
            HomeViewModel.MoviesLoadingState.Loading -> Text(
                text = "Loading...", modifier = Modifier.padding(10.dp)
            )
            is HomeViewModel.MoviesLoadingState.Success
            -> {
                MovieList(movies = state.movieState.movieList, modifier = modifier)
            }
        }
    }
}

@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieCard(movie = movie, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    val imageModifier = Modifier
        .height(180.dp)
        .padding(start = 10.dp, end = 10.dp)
    val context = LocalContext.current
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(movie.image).crossfade(true).build(),
            contentDescription = "Movie image",
            modifier = imageModifier.weight(2f),
            onError = {
                Toast.makeText(
                    context, it.toString(), Toast.LENGTH_LONG
                ).show()
            },
            contentScale = ContentScale.Fit,
//            error = painterResource(id = Color.Black.hashCode())
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = modifier
                .padding(end = 30.dp)
                .weight(2f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.title, movie.title),
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.year, movie.year.toString()),
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = stringResource(id = R.string.imdbId, movie.id),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Column(
            modifier = Modifier
                .height(180.dp)
                .weight(0.4f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Movie details",
                modifier = Modifier.clickable {

                })
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite movie",
                modifier = Modifier.clickable {

                })
        }
    }
}
