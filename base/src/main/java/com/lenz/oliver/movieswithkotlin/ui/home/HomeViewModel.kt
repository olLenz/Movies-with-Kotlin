package com.lenz.oliver.movieswithkotlin.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lenz.oliver.movieswithkotlin.base.R
import com.lenz.oliver.movieswithkotlin.repository.Repository
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.repository.models.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HomeViewModel
@Inject constructor(private val repository: Repository) : ViewModel() {

    val moviesLiveData = MutableLiveData<Resource<List<Movie>>>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun searchMovie(query: String) {
        if (query.isEmpty()) {
            getPopularMovies()
            return
        }

        compositeDisposable.add(
                repository.searchMovie(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                                onNext = {
                                    moviesLiveData.value = Resource.success(it.results)
                                },
                                onError = {
                                    moviesLiveData.value = Resource.error(R.string.generic_error)
                                }
                        )
        )
    }

    fun getPopularMovies() {
        compositeDisposable.add(
                repository.getPopularMovies()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                                onNext = {
                                    moviesLiveData.value = Resource.success(it.results)
                                },
                                onError = {
                                    moviesLiveData.value = Resource.error(R.string.generic_error)
                                }
                        )
        )
    }

}