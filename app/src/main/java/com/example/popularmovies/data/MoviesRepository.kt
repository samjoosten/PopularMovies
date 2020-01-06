package com.example.popularmovies.data

import com.example.popularmovies.model.MovieList
import retrofit2.Call

class MoviesRepository {
    private val moviesApi: MoviesApiService = MoviesApi.createApi()

    fun getMoviesByYear(releaseYear: Int): Call<MovieList> = moviesApi.getMoviesByYear(releaseYear)
}
