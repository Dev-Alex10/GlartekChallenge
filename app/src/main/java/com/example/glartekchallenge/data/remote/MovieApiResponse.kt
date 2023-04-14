package com.example.glartekchallenge.data.remote

import com.example.glartekchallenge.data.domain.Movie
import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class MovieApiRes(
    @SerializedName(value = "Search")
    val movieList: List<MovieApiSearchResponse>
)

data class MovieApiSearchResponse(
    @SerializedName(value = "Title")
    val title: String,
    @SerializedName(value = "Year")
    val year: String,
    @SerializedName(value = "imdbID")
    val imdbID: String,
    @SerializedName(value = "Type")
    val type: String,
    @SerializedName(value = "Poster")
    val poster: String
)

data class MovieApiSearchByTitleOrIdResponse(
    @SerializedName(value = "imdbID")
    val id: String,
    @SerializedName(value = "Title")
    val title: String,
    @SerializedName(value = "Year")
    val year: Int,
    @SerializedName(value = "imdbRating")
    val rating: Float,
    @SerializedName(value = "Genre")
    val genre: String,
    @SerializedName(value = "Plot")
    val plot: String,
    @SerializedName(value = "Director")
    val director: String,
    @SerializedName(value = "Actors")
    val actors: String,
    @SerializedName(value = "Poster")
    val poster: String
)

fun MovieApiSearchByTitleOrIdResponse.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        year = year,
        rating = rating,
        genre = genre,
        plot = plot,
        director = director,
        actors = actors,
        image = poster
    )
}

fun MovieApiSearchResponse.toDomain(): Movie {
    return Movie(
        id = imdbID,
        title = title,
        year = year.toInt(),
        rating = 5.0F,
        genre = "",
        plot = "",
        director = "",
        actors = "",
        image = poster.toString()
    )
}
