package com.example.popularmovies.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.popularmovies.R
import com.example.popularmovies.detail.DetailActivity
import com.example.popularmovies.model.Movie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val movies = arrayListOf<Movie>()
    private val movieAdapter = MovieAdapter(movies, onClick = {onMovieClicked(it)})
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        rvMovies.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvMovies.adapter = movieAdapter

        val layoutParams = RelativeLayout.LayoutParams(100, 100)
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        btnSubmit.setOnClickListener { onSubmitButtonClicked() }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.movies.observe(this, Observer {
            movies.clear()
            movies.addAll(it)
            movieAdapter.notifyDataSetChanged()
        })

    }

    private fun onSubmitButtonClicked() {
        if (etYear.text.isNullOrEmpty()) {
            val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
            toast.setText(R.string.val_release_year)
            toast.show()
        } else {
            mainViewModel.getMoviesByReleaseYear(etYear.text.toString().toInt())
            val inputMethodManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
        }
    }

    private fun onMovieClicked(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("Movie", movie)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
