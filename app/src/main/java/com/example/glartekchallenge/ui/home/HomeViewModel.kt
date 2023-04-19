package com.example.glartekchallenge.ui.home

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
    private val movieRepository: DefaultMovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> get() = _state //exposing state flow not mutable
    private var movieJob: Job? = null

    sealed interface MoviesLoadingState {
        //dataclass auto generates equals,hashcode & copy
        data class Success(val movieList: List<Movie>) : MoviesLoadingState
        data class Error(val error: String = "") : MoviesLoadingState
        object Loading : MoviesLoadingState
    }

    data class HomeViewState(
        val text: String = "",
        val movieState: MoviesLoadingState = MoviesLoadingState.Loading,
        val favoriteList: Set<String> = emptySet(),
        val totalPages: Int = 1,
        val currentPage: Int = 1
    )

    fun onTextChanged(text: String) {
        _state.update { it.copy(text = text) }
        getMovieList(text)
    }

    fun getMovieList(searchTerm: String, page: Int = 1) {
        movieJob?.cancel()

        _state.update { it.copy(movieState = MoviesLoadingState.Loading) }

        movieJob = viewModelScope.launch {
            delay(500)
            try {
                _state.update {
                    it.copy(
                        movieState = MoviesLoadingState.Success(
                            movieRepository.getMovies(
                                searchTerm, page
                            )
                        ),
                        currentPage = page,
                        totalPages = movieRepository.getResults(movieSearchTerm = searchTerm)
                            .totalResults?.div(10) ?: 1,
                        favoriteList = _state.value.favoriteList + userPreferencesRepository
                            .userPreferencesFlow.first().favoriteMovies
                    )
                }
            } catch (t: Throwable) {
                _state.update {
                    it.copy(
                        movieState = MoviesLoadingState.Error(
                            movieRepository.getResults(
                                movieSearchTerm = searchTerm
                            ).error ?: "Error"
                        )
                    )
                }
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
