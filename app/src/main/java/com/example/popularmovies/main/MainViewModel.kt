package com.example.popularmovies.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.popularmovies.data.MoviesRepository
import com.example.popularmovies.model.Movie
import com.example.popularmovies.model.MovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesRepository = MoviesRepository()
    val movies = MutableLiveData<Array<Movie>>()
    val error = MutableLiveData<String>()

    fun getMoviesByReleaseYear(releaseYear: Int) {
        moviesRepository.getMoviesByYear(releaseYear)
            .enqueue(object : Callback<MovieList> {
                override fun onResponse(
                    call: Call<MovieList>,
                    response: Response<MovieList>
                ) = if (response.isSuccessful) {
                    movies.value = response.body()?.movies
                } else {
                    error.value = "Failed to load all movies: ${response.errorBody().toString()}"
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    error.value = t.message

                }
            })
    }
}