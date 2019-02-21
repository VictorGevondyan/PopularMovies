package am.victor.popularmovies.mvp.presenters

import am.victor.popularmovies.PopularMoviesApplication
import am.victor.popularmovies.adapters.PopularMoviesAdapter
import am.victor.popularmovies.api.PopularMoviesAPI
import am.victor.popularmovies.mvp.models.PopularMovie
import am.victor.popularmovies.mvp.models.PopularMoviesWrapper
import am.victor.popularmovies.mvp.views.PopularMoviesView
import com.arellomobile.mvp.InjectViewState
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class PopularMoviesPresenter : BasePresenter<PopularMoviesView>() {

    @Inject
    lateinit var popularMoviesAPI: PopularMoviesAPI

    private var isInLoading: Boolean = false

    init {
        PopularMoviesApplication.popularMoviesAppComponent!!.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadPopularMovies(false)
    }

    fun loadNextPopularMovies(currentCount: Int) {
        val page = currentCount / PopularMoviesAdapter.PAGE_SIZE + 1

        loadData(page, true, false)
    }

    private fun loadPopularMovies(isRefreshing: Boolean) {
        loadData(1, false, isRefreshing)
    }

    private fun loadData(page: Int, isPageLoading: Boolean, isRefreshing: Boolean) {
        if (isInLoading) {
            return
        }
        isInLoading = true

        showProgress(isPageLoading, isRefreshing)

        val observable = popularMoviesAPI.getPopularMovies( PopularMoviesAPI.apiKey, page/*, PopularMoviesAdapter.PAGE_SIZE*/)

        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { popularMoviesWrapper: PopularMoviesWrapper -> Observable.from(popularMoviesWrapper.results)}
            .filter { item -> !item.adult }
            .toList()
            .subscribe({ popularMoviesList: List<PopularMovie> ->
                onLoadingFinish(isPageLoading, isRefreshing)
                onLoadingSuccess(isPageLoading, popularMoviesList)
            }, { error: Throwable ->
                onLoadingFinish(isPageLoading, isRefreshing)
                onLoadingFailed(error)
            })
        unsubscribeOnDestroy(subscription)
    }

    private fun onLoadingFinish(isPageLoading: Boolean, isRefreshing: Boolean) {
        isInLoading = false
        hideProgress(isPageLoading, isRefreshing)
    }

    private fun onLoadingSuccess(isPageLoading: Boolean, popularMoviesList: List<PopularMovie>) {

        val popularMovies = ArrayList(popularMoviesList)
        val maybeMore = popularMovies.size >= PopularMoviesAdapter.PAGE_SIZE
        if (isPageLoading) {
            viewState.addPopularMovies(popularMovies, maybeMore)
        } else {
            viewState.setPopularMovies(popularMovies, maybeMore)
        }

    }

    private fun onLoadingFailed(error: Throwable) {
        viewState.showError(error.toString())
    }

    private fun showProgress(isPageLoading: Boolean, isRefreshing: Boolean) {
        if (isPageLoading) {
            return
        }

        if (!isRefreshing) {
            viewState.showListProgress()
        }
    }

    private fun hideProgress(isPageLoading: Boolean, isRefreshing: Boolean) {
        if (isPageLoading) {
            return
        }

        if (!isRefreshing) {
            viewState.hideListProgress()
        }
    }

    fun onErrorCancel() {
        viewState.hideError()
    }

}