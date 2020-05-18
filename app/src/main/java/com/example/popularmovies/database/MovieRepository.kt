package com.example.popularmovies.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popularmovies.api.MovieApi
import com.example.popularmovies.api.MovieList
import com.example.popularmovies.common.Common
import com.example.popularmovies.model.MovieItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList



class MovieRepository {

    val movieList = MutableLiveData<List<MovieItem>>()
    private fun removeQuotes(str: String): String {
        return str.replace(Common.STRING_QUOTE, Common.STRING_EMPTY)
    }

    fun getMovieList(): LiveData<List<MovieItem>> {
        return movieList
    }

    private fun setMoviesLoadState() {
        val moviesLoadState = ArrayList<MovieItem>()
        for (i in Common.MOVIE_START_COUNTER..Common.DEFAULT_PAGE_MOVIES) {
            moviesLoadState.add(MovieItem(i.toString(), i.toString(), i.toString(), i.toString(),
                i.toString(), i.toString(), true))
        }
        movieList.value = moviesLoadState
    }

    fun getApiData(year: String, apiKey: String) {
        setMoviesLoadState()

        val moviesFound = ArrayList<MovieItem>()

        val retrofit = Retrofit.Builder()
            .baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val movieApi = retrofit.create(MovieApi::class.java)
        val jsonCall = movieApi.getMovies(apiKey, Common.DEFAULT_LANG, Common.DEFAULT_SORT, false, false, Common.DEFAULT_PAGE, year)
        jsonCall.enqueue(object: Callback<MovieList>{
                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                }

                override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                    if (!response.isSuccessful) {
                        return
                    }
                    var results = response.body()?.getResults()
                    for (i in 0 until results?.size()!!) {
                        var result = results?.get(i)?.asJsonObject
                        val posterPath = removeQuotes(result?.get(Common.JSON_POSTER).toString())
                        val backdropPath = removeQuotes(result?.get(Common.JSON_BACKROP).toString())
                        val title = removeQuotes(result?.get(Common.JSON_TITLE).toString())
                        val overview = removeQuotes(result?.get(Common.JSON_OVERVIEW).toString())
                        val releaseDate = removeQuotes(result?.get(Common.JSON_RELEASE).toString())
                        val voteAverage = removeQuotes(result?.get(Common.JSON_RATING).toString())
                        moviesFound.add(MovieItem(posterPath, backdropPath, title, overview,
                            releaseDate, voteAverage, false))
                    }
                    movieList.value = moviesFound
                }
            })
    }
}