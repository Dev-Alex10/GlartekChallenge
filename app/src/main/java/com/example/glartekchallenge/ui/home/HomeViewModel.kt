package com.example.glartekchallenge.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glartekchallenge.data.domain.DefaultMovieRepository
import com.example.glartekchallenge.data.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DefaultMovieRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> get() = _state //exposing state flow not mutable
    private var movieJob: Job? = null
    private var response: List<Movie> = emptyList()

    sealed interface MoviesLoadingState {
        //dataclass auto generates equals,hashcode & copy
        data class Success(val movieList: List<Movie>) : MoviesLoadingState
        data class Error(val t: Throwable) : MoviesLoadingState
        object Loading : MoviesLoadingState
    }

    data class ViewState(
        val text: String = "",
        val movieState: MoviesLoadingState = MoviesLoadingState.Loading
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
                Log.d(TAG, "getMovieList: $response")
                _state.update { it.copy(movieState = MoviesLoadingState.Success(response)) }
            } catch (t: Throwable) {
                _state.update { it.copy(movieState = MoviesLoadingState.Error(t)) }
            }
        }
    }
}
