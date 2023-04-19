package com.example.glartekchallenge.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glartekchallenge.data.domain.DefaultMovieRepository
import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.data.userpreferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val movieRepository: DefaultMovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {


    private val _state = MutableStateFlow(FavoriteMovieViewState())
    val state: StateFlow<FavoriteMovieViewState> get() = _state //exposing state flow not mutable

    init {
        viewModelScope.launch {
            val movieIds = userPreferencesRepository.userPreferencesFlow
                .first().favoriteMovies
            _state.update {
                it.copy(
                    favoriteList = _state.value.favoriteList +
                            movieIds.map { movieId ->
                                movieRepository.getMovieById(movieId)
                            }
                )
            }
        }
    }

    data class FavoriteMovieViewState(
        val favoriteList: List<Movie> = emptyList()
    )

    fun onFavoriteClick(movie: Movie) {
        _state.update {
            it.copy(
                favoriteList = _state.value.favoriteList - movie
            )
        }
        viewModelScope.launch {
            userPreferencesRepository.updateIsFavoriteMovieId(movie.id, false)
        }
    }

    fun isFavorite(movie: Movie): Boolean {
        return _state.value.favoriteList.contains(movie)
    }
}
