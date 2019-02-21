package am.victor.popularmovies.di


import am.victor.popularmovies.api.PopularMoviesAPI
import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by victor on 11/30/18.
 */

@Module
class AppModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun providePopularMoviesAPI(): PopularMoviesAPI {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(PopularMoviesAPI.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val popularMoviesAPI = retrofit.create(PopularMoviesAPI::class.java)
        return popularMoviesAPI

    }

}