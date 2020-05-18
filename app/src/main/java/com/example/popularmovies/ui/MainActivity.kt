package com.example.popularmovies.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.common.Common
import com.example.popularmovies.model.MovieItem
import com.example.popularmovies.model.MovieListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieListViewModel: MovieListViewModel
    private val movieList = arrayListOf<MovieItem>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        movieListViewModel.getMovieLiveData().observe(this, Observer {
            assignAdapter()
            movieList.clear()
            movieList.addAll(it)
        })
    }

    private fun initViews() {
        supportActionBar?.title = getString(R.string.main_activity_title)
        val gridLayoutManager = GridLayoutManager(this, Common.DEFAULT_SPAN_COUNT,
            RecyclerView.VERTICAL, false)
        rvMovies.layoutManager = gridLayoutManager


        rvMovies.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rvMovies.viewTreeObserver.removeOnGlobalLayoutListener(this)
                gridLayoutManager.spanCount = calculateSpanCount()
                gridLayoutManager.requestLayout()
            }
        })
        assignAdapter()
        btnSubmit.setOnClickListener { onSubmitClick() }
    }

    private fun calculateSpanCount(): Int{
        val viewWidth = rvMovies.measuredWidth
        val moviePosterWidth = resources.getDimension(R.dimen.poster_width)
        val moviePosterMargin = resources.getDimension(R.dimen.margin_medium)
        val spanCount = Math.floor((viewWidth / (moviePosterWidth +
                moviePosterMargin)).toDouble()).toInt()
        return if (spanCount >= Common.DEFAULT_SPAN_COUNT) spanCount else Common.DEFAULT_SPAN_COUNT
    }

    private fun onSubmitClick() {
        if (etYear.text.toString() == Common.lastYear)
            return
        movieListViewModel.getMoviesByYear(etYear.text.toString(), getString(R.string.movie_db_api_key))
        Common.lastYear = etYear.text.toString()
    }

    private fun onMovieClick(movieItem: MovieItem) {
        if (movieItem.loading)
            return
        val detailsActivityIntent = Intent(this, DetailsActivity::class.java)
        detailsActivityIntent.putExtra(DETAILS_EXTRA, movieItem)
        startActivity(detailsActivityIntent)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        movieListViewModel.getMoviesByYear(
            etYear.text.toString(),
            getString(R.string.movie_db_api_key)
        )
    }

    fun assignAdapter() {
        movieAdapter = MovieAdapter(
            movieList,
            { movieItem -> onMovieClick(movieItem) })
        rvMovies.adapter = movieAdapter
    }
}
