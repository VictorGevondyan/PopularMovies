package am.victor.popularmovies.adapters

import am.victor.popularmovies.R
import am.victor.popularmovies.mvp.models.PopularMovie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpDelegate
import java.util.*

class PopularMoviesAdapter(
    private var popularMoviesArrayList: ArrayList<PopularMovie>,
    private var mScrollToBottomListener: OnScrollToBottomListener,
    parentDelegate: MvpDelegate<*>
) : MvpBaseAdapter(parentDelegate, "0") {

    companion object {
        const val PAGE_SIZE = 20
        const val POPULAR_MOVIE_VIEW_TYPE = 0
        const val PROGRESS_VIEW_TYPE = 1
    }

    private var maybeMore: Boolean = false

    fun setPopularMovies(repositories: ArrayList<PopularMovie>, maybeMore: Boolean) {
        popularMoviesArrayList = repositories
        dataSetChanged(maybeMore)
    }

    fun addPopularMovies(repositories: ArrayList<PopularMovie>, maybeMore: Boolean) {
        popularMoviesArrayList.addAll(repositories)
        dataSetChanged(maybeMore)
    }

    private fun dataSetChanged(maybeMore: Boolean) {
        this.maybeMore = maybeMore
        notifyDataSetChanged()
    }

    fun getPopularMoviesCount(): Int {
        return popularMoviesArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == popularMoviesArrayList.size) PROGRESS_VIEW_TYPE else POPULAR_MOVIE_VIEW_TYPE
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getCount() = popularMoviesArrayList.size + if (maybeMore) 1 else 0

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItem(position: Int): PopularMovie {
        return popularMoviesArrayList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var popularMovieConvertView = convertView
        if (getItemViewType(position) == PROGRESS_VIEW_TYPE) {
            mScrollToBottomListener.onScrollToBottom()
            return ProgressBar(parent.context)
        }

        val holder: PopularMovieViewHolder
        if (popularMovieConvertView != null) {
            holder = popularMovieConvertView.tag as PopularMovieViewHolder
        } else {
            popularMovieConvertView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_popular_movie, parent, false)
            holder = PopularMovieViewHolder(
                popularMovieConvertView
            )
            popularMovieConvertView!!.tag = holder
        }

        val popularMovie = getItem(position)

        holder.bindPopularMovie(popularMovie)

        return popularMovieConvertView

    }

    interface OnScrollToBottomListener {
        fun onScrollToBottom()
    }

}