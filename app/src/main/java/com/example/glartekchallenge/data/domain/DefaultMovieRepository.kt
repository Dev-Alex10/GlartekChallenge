package com.example.glartekchallenge.data.domain

import com.example.glartekchallenge.data.remote.MoviesRemoteSource
import javax.inject.Inject

class DefaultMovieRepository @Inject constructor(private val moviesRemoteSource: MoviesRemoteSource) :
    MovieRepository {
    override suspend fun getMovies(movieSearchTerm: String): List<Movie> {
        return moviesRemoteSource.getMovies(movieSearchTerm)
    }

    override suspend fun getMovieByTitle(movieTitle: String): Movie {
        return moviesRemoteSource.getMovieByTitle(movieTitle)
    }

    override suspend fun getMovieById(movieId: String): Movie {
        return moviesRemoteSource.getMovieById(movieId)
    }
}
