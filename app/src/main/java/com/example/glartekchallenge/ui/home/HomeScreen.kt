package com.example.glartekchallenge.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glartekchallenge.R
import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.ui.utils.ImageComponent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onInfoClick: (Movie) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    Column(modifier = modifier) {
        TextField(
            value = state.text,
            onValueChange = viewModel::onTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier.padding(10.dp)
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.searchByName))
            },
            singleLine = true,
        )
        when (state.movieState) {
            is HomeViewModel.MoviesLoadingState.Error -> Text(
                text = state.movieState.error,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            HomeViewModel.MoviesLoadingState.Loading -> {
//                Text(text = state.favoriteList.toString())
                Text(
                    text = stringResource(id = R.string.loading),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is HomeViewModel.MoviesLoadingState.Success
            -> {
                Pagination(viewModel)
                MovieList(
                    movies = state.movieState.movieList,
                    modifier = modifier,
                    onInfoClick = onInfoClick,
                    onFavoriteClick = {
                        viewModel.onFavoriteClick(it.id, true)
                    },
                    isFavorite = { viewModel.isFavorite(it.id) }
                )
            }
        }
    }
}

@Composable
private fun Pagination(
    viewModel: HomeViewModel
) {
    val state = viewModel.state.collectAsState().value
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        if (state.currentPage > 1) {
            IconButton(onClick = {
                viewModel.getMovieList(searchTerm = state.text, page = state.currentPage - 1)
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.previous)
                )
            }
        }
        Button(onClick = { }) {
            Text(
                text = stringResource(
                    id = R.string.paginator,
                    state.currentPage,
                    state.totalPages
                )
            )
        }
        IconButton(onClick = {
            viewModel.getMovieList(searchTerm = state.text, page = state.currentPage + 1)
        }) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.next)
            )
        }
    }
}

@Composable
fun MovieList(
    movies: List<Movie>, modifier: Modifier = Modifier,
    onInfoClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    isFavorite: (Movie) -> Boolean
) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                modifier = Modifier.padding(top = 10.dp),
                onInfoClick = onInfoClick,
                onFavoriteClick = { onFavoriteClick(movie) },
                isFavorite = isFavorite
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onInfoClick: (Movie) -> Unit,
    onFavoriteClick: (Movie) -> Unit,
    isFavorite: (Movie) -> Boolean
) {
    val imageModifier = Modifier
        .height(180.dp)
        .padding(start = 10.dp, end = 10.dp)
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        ImageComponent(modifier = imageModifier.weight(2f), movie = movie)
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
                contentDescription = stringResource(id = R.string.details),
                modifier = Modifier.clickable {
                    onInfoClick(movie)
                }
            )
            //IconTogleButton
            if (!isFavorite(movie)) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favorite_movie),
                    modifier = Modifier.clickable {
                        onFavoriteClick(movie)
                    })
            } else {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.unfavorite_movie),
                    modifier = Modifier.clickable {
                        onFavoriteClick(movie)
                    })
            }
        }
    }
}
