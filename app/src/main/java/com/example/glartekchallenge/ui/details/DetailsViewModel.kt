package com.example.glartekchallenge.ui.details

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glartekchallenge.data.domain.DefaultMovieRepository
import com.example.glartekchallenge.data.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DefaultMovieRepository,
    savedState: SavedStateHandle
) : ViewModel() {
    private val movieId: String = requireNotNull(savedState["movieId"])
    private val _state = MutableStateFlow(
        DetailsViewState(
            Movie(
                id = "0",
                title = "title",
                year = 2000,
                image = "poster",
                rating = "0.0",
                genre = "genre",
                director = "director",
                actors = "actors",
                plot = "plot",
            )
        )
    )
    val state: MutableStateFlow<DetailsViewState> get() = _state //exposing state flow not mutable

    init {
        getMovieDetails()
    }


    data class DetailsViewState(
        val movie: Movie,
    )

    private fun getMovieDetails(): Movie {
        viewModelScope.launch {
            _state.update { it.copy(movie = repository.getMovieById(movieId)) }
        }
        return _state.value.movie
    }
}
