package com.example.glartekchallenge.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("?apikey=$API_KEY&type=movie")
    suspend fun getSearchMovies(
        @Query(value = "s") search: String,
        @Query(value = "page") page: Int
    ): MovieApiRes

    @GET("?apikey=$API_KEY&plot=full&type=movie")
    suspend fun getSearchMoviesByTitle(@Query(value = "t") searchTitle: String): MovieApiSearchByTitleOrIdResponse

    @GET("?apikey=$API_KEY&plot=full&type=movie")
    suspend fun getSearchMoviesById(@Query(value = "i") searchId: String): MovieApiSearchByTitleOrIdResponse
}
