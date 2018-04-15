package com.lenz.oliver.movieswithkotlin.repository

import android.arch.lifecycle.LiveData
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import android.arch.lifecycle.MutableLiveData
import com.lenz.oliver.movieswithkotlin.repository.models.pages.MoviePage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class Repository(private val api: TmdbApi) {

    fun getPopularMovies(): MutableLiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        api.getPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = {
                            data.value = it.results
                        }
                )

        return data
    }

    fun getRecommendations(id: Long): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()
        api.getRecommendationsForMovie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = {
                            data.value = it.results
                        }
                )

        return data
    }

    fun getMovieDetails(id: Long): LiveData<Movie> {
        val data = MutableLiveData<Movie>()
        api.getMovieDetails(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = {
                            data.value = it
                        }
                )

        return data
    }

    fun searchMovie(query: String, data: MutableLiveData<List<Movie>>) {
        api.searchMovie(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = {
                            data.value = it.results
                        }
                )
    }
}