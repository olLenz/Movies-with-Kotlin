package com.lenz.oliver.movieswithkotlin.ui.recommendations

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.lenz.oliver.movieswithkotlin.repository.Repository
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import javax.inject.Inject

class RecommendationsViewModel
@Inject constructor (application: Application, private val repository: Repository)
    : AndroidViewModel(application) {

    private var recommendationsLiveData: LiveData<List<Movie>>? = null

    fun getRecommendationsLiveData(movie: Movie?): LiveData<List<Movie>>? {
        if (movie?.id == null) {
            return null
        }

        recommendationsLiveData = repository.getRecommendations(movie.id)
        return recommendationsLiveData
    }

}