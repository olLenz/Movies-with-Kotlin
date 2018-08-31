package com.lenz.oliver.movieswithkotlin.repository


class Repository(private val api: TmdbApi) {

    /**
     * Get a list of currently popular movies.
     */
    fun getPopularMovies() = api.getPopularMovies()

    /**
     * Get a a list of recommendations for the given movie.
     */
    fun getRecommendations(id: Long) = api.getRecommendationsForMovie(id)

    /**
     * Get details to the given movie.
     */
    fun getMovieDetails(id: Long) = api.getMovieDetails(id)

    /**
     * Search all movies for the given query.
     */
    fun searchMovie(query: String) = api.searchMovie(query)
}