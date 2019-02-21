package am.victor.popularmovies.adapters

import am.victor.popularmovies.mvp.models.PopularMovie
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_popular_movie.view.*

class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView = itemView.title
    private val descriptionTextView = itemView.description

    fun bindPopularMovie(popularMovie: PopularMovie) {
        titleTextView.text = popularMovie.title
        descriptionTextView.text = popularMovie.overview
    }

}