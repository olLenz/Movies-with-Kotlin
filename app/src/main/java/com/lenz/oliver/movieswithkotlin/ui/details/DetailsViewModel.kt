package com.lenz.oliver.movieswithkotlin.ui.details

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.lenz.oliver.movieswithkotlin.repository.Repository
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import javax.inject.Inject


class DetailsViewModel
@Inject constructor (application: Application, private val repository: Repository)
    : AndroidViewModel(application) {

    private var movieLiveData: LiveData<Movie>? = null

    fun getMovieDetails(movie: Movie?): LiveData<Movie>? {
        if (movie?.id == null) {
            return null
        }

        movieLiveData = repository.getMovieDetails(movie.id)
        return movieLiveData
    }

}