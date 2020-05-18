package com.example.popularmovies.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies.database.MovieRepository

class MovieListViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    fun getMoviesByYear(year: String, apiKey: String){
        movieRepository.getApiData(year, apiKey)
    }

    fun getMovieLiveData(): LiveData<List<MovieItem>>{
        return movieRepository.getMovieList()
    }
}