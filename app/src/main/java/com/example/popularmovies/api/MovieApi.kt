package com.example.popularmovies.api
import com.example.popularmovies.common.Common
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(Common.BASE_QUERY)
    fun getMovies(
        @Query(Common.KEY_QUERY) apiKey: String,
        @Query(Common.LANG_QUERY) language: String,
        @Query(Common.SORT_QUERY) sortBy: String,
        @Query(Common.ADULT_QUERY) adult: Boolean,
        @Query(Common.VIDEO_QUERY) video: Boolean,
        @Query(Common.PAGE_QUERY) page: Int,
        @Query(Common.YEAR_QUERY) year: String
    ): Call<MovieList>

    companion object{
        operator fun invoke(): MovieApi{
            return Retrofit.Builder()
                .baseUrl(Common.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)
        }
    }
}