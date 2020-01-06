package com.example.popularmovies.model

import com.google.gson.annotations.SerializedName

@Suppress("ArrayInDataClass")
data class MovieList (
    @SerializedName("results") var movies: Array<Movie>
)