package am.victor.popularmovies.activity

import am.victor.popularmovies.R
import am.victor.popularmovies.adapters.PopularMoviesAdapter
import am.victor.popularmovies.mvp.models.PopularMovie
import am.victor.popularmovies.mvp.presenters.PopularMoviesPresenter
import am.victor.popularmovies.mvp.views.PopularMoviesView
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity() , PopularMoviesView, PopularMoviesAdapter.OnScrollToBottomListener{

    @InjectPresenter
    lateinit var popularMoviesPresenter: PopularMoviesPresenter
    private var popularMoviesAdapter: PopularMoviesAdapter? = null
    private var errorDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popularMoviesArray = ArrayList<PopularMovie>()
        popularMoviesAdapter = PopularMoviesAdapter(popularMoviesArray, this, mvpDelegate)
        popular_movies_list_view.adapter = popularMoviesAdapter

    }

    override fun showListProgress() {
        popular_movies_list_view.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideListProgress() {
        popular_movies_list_view.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    override fun showError(message: String) {
        errorDialog = AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setOnCancelListener { popularMoviesPresenter.onErrorCancel() }
            .show()
    }

    override fun hideError() {
        if (errorDialog != null && errorDialog!!.isShowing) {
            errorDialog!!.hide()
        }
    }

    override fun setPopularMovies(popularMovies: ArrayList<PopularMovie>, maybeMore: Boolean) {
        popularMoviesAdapter!!.setPopularMovies(popularMovies, maybeMore)
    }

    override fun addPopularMovies(popularMovies: ArrayList<PopularMovie>, maybeMore: Boolean) {
        popularMoviesAdapter!!.addPopularMovies(popularMovies, maybeMore)
    }

    override fun onScrollToBottom() {
        popularMoviesPresenter.loadNextPopularMovies(popularMoviesAdapter!!.getPopularMoviesCount())
    }

    override fun onDestroy() {
        if (errorDialog != null && errorDialog!!.isShowing) {
            errorDialog!!.setOnCancelListener(null)
            errorDialog!!.dismiss()
        }

        super.onDestroy()
    }

}
