package am.victor.popularmovies

import am.victor.popularmovies.di.AppComponent
import am.victor.popularmovies.di.AppModule
import am.victor.popularmovies.di.DaggerAppComponent
import android.app.Application
import android.support.annotation.VisibleForTesting

class PopularMoviesApplication : Application() {

    companion object {
        var popularMoviesAppComponent: AppComponent? = null
    }

    override fun onCreate() {

        super.onCreate()
        popularMoviesAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    }

}