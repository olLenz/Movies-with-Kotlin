package com.lenz.oliver.movieswithkotlin.ui.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.lenz.oliver.movieswithkotlin.repository.Repository
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import javax.inject.Inject


class HomeViewModel
@Inject constructor (application: Application, repository: Repository)
    : AndroidViewModel(application) {

    private var moviesLiveData: LiveData<List<Movie>>? = null

    init {

        moviesLiveData = repository.getPopularMovies()

    }

    fun getMoviesLiveData() = moviesLiveData

}