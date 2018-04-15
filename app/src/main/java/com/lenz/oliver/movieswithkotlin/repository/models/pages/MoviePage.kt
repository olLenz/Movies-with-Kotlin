package com.lenz.oliver.movieswithkotlin.repository.models.pages

import com.lenz.oliver.movieswithkotlin.repository.models.Movie

data class MoviePage (
        val results: List<Movie>?
)