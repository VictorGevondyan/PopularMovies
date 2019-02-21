package am.victor.popularmovies.mvp.views

import am.victor.popularmovies.mvp.models.PopularMovie
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PopularMoviesView : MvpView {

    fun showError(message: String)

    fun hideError()

    fun showListProgress()

    fun hideListProgress()

    fun setPopularMovies(popularMovies: ArrayList<PopularMovie>, maybeMore: Boolean)

    @StateStrategyType(AddToEndStrategy::class)
    fun addPopularMovies(popularMovies: ArrayList<PopularMovie>, maybeMore: Boolean)

}
