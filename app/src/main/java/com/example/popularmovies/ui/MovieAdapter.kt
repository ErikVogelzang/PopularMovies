package com.example.popularmovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.common.Common
import com.example.popularmovies.model.MovieItem
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(val movieList: List<MovieItem>, private val onClick: (MovieItem) -> Unit) :
RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(movieList[adapterPosition]) }
        }

        fun bind(movieItem: MovieItem, pos: Int) {
            itemView.tvRank.text = (pos + Common.INCREMENT_BY_ONE).toString() + Common.STRING_DOT
            if (!movieItem.loading) {
                Common.fetchImageGlide(context, itemView.ivMovie, itemView.pbLoading,
                    movieItem.posterPath)
            }
            else {
                itemView.ivMovie.visibility = View.GONE
                itemView.pbLoading.visibility = View.VISIBLE
            }

        }
    }
}