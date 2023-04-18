package com.example.glartekchallenge.ui.utils

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.glartekchallenge.R

@Composable
fun MovieAppBar(
    canNavigateBack: Boolean,
    currentScreenTitle: String,
    navigateUp: () -> Unit,
    shareOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = currentScreenTitle) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (currentScreenTitle == "Movie Details")
                IconButton(onClick = shareOnClick) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share deep link")
                }
        }
    )
}
