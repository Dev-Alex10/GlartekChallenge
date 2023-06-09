package com.example.glartekchallenge.data.remote

import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.data.domain.MovieResults
import javax.inject.Inject

class MoviesRemoteSource @Inject constructor(private val moviesAPI: MoviesAPI) {
    suspend fun getResults(movieSearchTerm: String): MovieResults {
        return moviesAPI.getSearchMovies(movieSearchTerm, 1).toDomain()
    }

    suspend fun getMovies(movieSearchTerm: String, page: Int = 1): List<Movie> {
        return moviesAPI.getSearchMovies(movieSearchTerm, page).movieList.map {
            it.toDomain()
        }
    }

    suspend fun getMovieByTitle(movieTitle: String): Movie {
        return moviesAPI.getSearchMoviesByTitle(movieTitle).toDomain()
    }

    suspend fun getMovieById(movieId: String): Movie {
        return moviesAPI.getSearchMoviesById(movieId).toDomain()
    }
}
