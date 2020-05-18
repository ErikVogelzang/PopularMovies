package com.example.popularmovies.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.popularmovies.R
import com.example.popularmovies.common.Common
import com.example.popularmovies.model.MovieItem
import kotlinx.android.synthetic.main.activity_details.*

const val DETAILS_EXTRA = "DETAILS_EXTRA"

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = Common.STRING_EMPTY
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val movieItem = intent.getParcelableExtra<MovieItem>(DETAILS_EXTRA)
        Common.fetchImageGlide(application.applicationContext, ivPoster, pbPoster,
            movieItem.posterPath)
        Common.fetchImageGlide(application.applicationContext, ivBackdrop, pbBackdrop,
            movieItem.backdropPath)
        tvTitle.text = movieItem.title
        tvOverview.text = movieItem.overview
        tvRelease.text = application.applicationContext.getString(R.string.release_text, movieItem.releaseDate)
        tvRating.text = movieItem.voteAverage
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
