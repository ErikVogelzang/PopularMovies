package com.example.popularmovies.api

import com.google.gson.JsonArray

class MovieList(
    private var results: JsonArray
) {
    fun getResults(): JsonArray = results
}