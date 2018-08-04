package com.lenz.oliver.movieswithkotlin.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.lenz.oliver.movieswithkotlin.repository.Repository
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import javax.inject.Inject


class HomeViewModel
@Inject constructor(application: Application, private val repository: Repository)
    : AndroidViewModel(application) {

    private var moviesLiveData: MutableLiveData<List<Movie>>? = null

    init {

        moviesLiveData = repository.getPopularMovies()

    }

    fun getMoviesLiveData() = moviesLiveData

    fun searchMovie(query: String) {
        if (query.isEmpty()) {
            moviesLiveData?.let {
                repository.getPopularMovies(it)
            }
            return
        }

        moviesLiveData?.let {
            repository.searchMovie(query, it)
        }
    }

}