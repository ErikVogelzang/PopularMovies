package com.example.popularmovies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: String,
    var loading: Boolean
) : Parcelable