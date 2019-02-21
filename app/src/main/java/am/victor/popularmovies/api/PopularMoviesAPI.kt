package am.victor.popularmovies.api

import am.victor.popularmovies.mvp.models.PopularMoviesWrapper
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface PopularMoviesAPI {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val apiKey = "f215d469dd16a804775c5988e16718ac"
    }

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Observable<PopularMoviesWrapper>

}