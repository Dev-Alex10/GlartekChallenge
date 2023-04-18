package com.example.glartekchallenge.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glartekchallenge.data.domain.DefaultMovieRepository
import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.data.userpreferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultMovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> get() = _state //exposing state flow not mutable
    private var movieJob: Job? = null
    private var response: List<Movie> = emptyList()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(favoriteList = userPreferencesRepository.userPreferencesFlow.first().favoriteMovies)
            }
        }
    }

    sealed interface MoviesLoadingState {
        //dataclass auto generates equals,hashcode & copy
        data class Success(val movieList: List<Movie>) : MoviesLoadingState
        data class Error(val t: Throwable) : MoviesLoadingState
        object Loading : MoviesLoadingState
    }

    data class HomeViewState(
        val text: String = "",
        val movieState: MoviesLoadingState = MoviesLoadingState.Loading,
        val favoriteList: Set<String> = emptySet(),
    )

    fun onTextChanged(text: String) {
        _state.update { it.copy(text = text) }
        response = emptyList()
        getMovieList(text)
    }

    private fun getMovieList(searchTerm: String) {
        movieJob?.cancel()

        _state.update { it.copy(movieState = MoviesLoadingState.Loading) }

        movieJob = viewModelScope.launch {
            delay(500)
            try {
                response = repository.getMovies(searchTerm)
                _state.update { it.copy(movieState = MoviesLoadingState.Success(response)) }
            } catch (t: Throwable) {
                _state.update { it.copy(movieState = MoviesLoadingState.Error(t)) }
            }
        }
    }

    fun onFavoriteClick(movieId: String, newValue: Boolean) {
        var bool = newValue
        if (movieId in _state.value.favoriteList && newValue) {
            bool = false
        }
        if (bool) {
            _state.update {
                it.copy(
                    favoriteList = _state.value.favoriteList + movieId
                )
            }
        } else {
            _state.update {
                it.copy(
                    favoriteList = _state.value.favoriteList - movieId
                )
            }
        }
        viewModelScope.launch {
            userPreferencesRepository.updateIsFavoriteMovieId(movieId, bool)
        }
    }

    fun isFavorite(id: String): Boolean {
        return _state.value.favoriteList.contains(id)
    }
}
