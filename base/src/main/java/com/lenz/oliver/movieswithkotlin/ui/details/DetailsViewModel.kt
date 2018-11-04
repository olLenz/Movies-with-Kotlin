package com.lenz.oliver.movieswithkotlin.ui.details

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


class DetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var movieLiveData = MutableLiveData<Resource<Movie>>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun getMovieDetails(movie: Movie?): MutableLiveData<Resource<Movie>>? {
        if (movie?.id == null) {
            return null
        }

        compositeDisposable.add(
                repository.getMovieDetails(movie.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                                onNext = {
                                    movieLiveData.value = Resource.success(it)
                                },
                                onError = {
                                    movieLiveData.value = Resource.error(R.string.generic_error)
                                }
                        )
        )

        return movieLiveData
    }

}