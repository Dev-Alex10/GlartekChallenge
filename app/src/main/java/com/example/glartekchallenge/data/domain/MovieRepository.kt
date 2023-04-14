package com.example.glartekchallenge.data.domain

interface MovieRepository {
    suspend fun getMovies(movieSearchTerm: String): List<Movie>
    suspend fun getMovieByTitle(movieTitle: String): Movie
    suspend fun getMovieById(movieId: String): Movie
}
