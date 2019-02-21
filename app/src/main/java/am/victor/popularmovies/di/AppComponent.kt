package am.victor.popularmovies.di

import am.victor.popularmovies.api.PopularMoviesAPI
import am.victor.popularmovies.mvp.presenters.PopularMoviesPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by victor on 11/30/18.
 */

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(popularMoviesAPI: PopularMoviesAPI)

    fun inject(popularMoviesPresenter: PopularMoviesPresenter)
}