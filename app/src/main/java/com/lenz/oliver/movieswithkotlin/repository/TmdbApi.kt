package com.lenz.oliver.movieswithkotlin.repository

import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.repository.models.pages.MoviePage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApi {

    @GET("movie/popular")
    fun getPopularMovies(): Observable<MoviePage>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationsForMovie(@Path(value = "movie_id") movieId: Long): Observable<MoviePage>

    @GET("movie/{movie_id}?append_to_response=credits")
    fun getMovieDetails(@Path(value = "movie_id")movieId: Long): Observable<Movie>
}