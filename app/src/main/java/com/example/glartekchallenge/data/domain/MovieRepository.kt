package com.example.glartekchallenge.data.domain

interface MovieRepository {
    suspend fun getResults(movieSearchTerm: String): MovieResults
    suspend fun getMovies(movieSearchTerm: String, page: Int = 1): List<Movie>
    suspend fun getMovieByTitle(movieTitle: String): Movie
    suspend fun getMovieById(movieId: String): Movie
}
