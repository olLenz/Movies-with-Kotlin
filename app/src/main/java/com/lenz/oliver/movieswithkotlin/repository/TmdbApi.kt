package com.lenz.oliver.movieswithkotlin.repository

import com.lenz.oliver.movieswithkotlin.repository.models.MoviePage
import io.reactivex.Observable
import retrofit2.http.GET

interface TmdbApi {

    @GET("discover/movie?sort_by=popularity.desc")
    fun getPopularMovies(): Observable<MoviePage>

}