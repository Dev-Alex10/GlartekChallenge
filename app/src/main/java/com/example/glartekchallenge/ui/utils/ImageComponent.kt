package com.example.glartekchallenge.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.glartekchallenge.R
import com.example.glartekchallenge.data.domain.Movie

@Composable
fun ImageComponent(modifier: Modifier, movie: Movie) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context).data(movie.image).crossfade(true).build(),
        contentDescription = stringResource(id = R.string.movie_image),
        modifier = modifier,
        onError = {
        },
        contentScale = ContentScale.Fit,
        error = painterResource(id = R.drawable.cancel),
        alignment = Alignment.Center
    )
}
