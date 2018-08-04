package com.lenz.oliver.movieswithkotlin.repository

import android.arch.lifecycle.LiveData
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import android.arch.lifecycle.MutableLiveData
import com.lenz.oliver.movieswithkotlin.repository.models.pages.MoviePage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class Repository(private val api: TmdbApi) {

    /**
     * Get a list of currently popular movies asynchronously.
     */
    fun getPopularMovies(data: MutableLiveData<List<Movie>>? = null): MutableLiveData<List<Movie>> {
        val newData = MutableLiveData<List<Movie>>()
        api.getPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onNext = {
                            if (data != null) {
                                data.value = it.results
                            } else {
                                newData.value = it.results
                            }
                        }
                )

        return newData
    }

    /**
     * Get a a list of recommendations for the given movie asynchronously.
     */
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

    /**
     * Get details to the given movie asynchronously.
     */
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

    /**
     * Search all movies for the given query asynchronously.
     */
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