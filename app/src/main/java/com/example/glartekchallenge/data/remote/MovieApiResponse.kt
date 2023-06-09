package com.example.glartekchallenge.data.remote

import com.example.glartekchallenge.data.domain.Movie
import com.example.glartekchallenge.data.domain.MovieResults
import com.google.gson.annotations.SerializedName

data class MovieApiRes(
    @SerializedName(value = "Search")
    val movieList: List<MovieApiSearchResponse>,
    @SerializedName(value = "totalResults")
    val totalResults: String?,
    @SerializedName(value = "Error")
    val error : String?
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
    val rating: String,
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
        rating = "5.0",
        genre = "",
        plot = "",
        director = "",
        actors = "",
        image = poster
    )
}

fun MovieApiRes.toDomain(): MovieResults {
    return MovieResults(
        totalResults = totalResults?.toInt(),
        error = error
    )
}
