package com.example.glartekchallenge.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val year: Int,
    val rating: String,
    val genre: String,
    val plot: String,
    val director: String,
    val actors: String,
    val image: String
) : Parcelable
data class MovieResults(
    val totalResults: Int?,
    val error: String?
)
