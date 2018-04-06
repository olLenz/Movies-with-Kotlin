package com.lenz.oliver.movieswithkotlin.utils

private const val posterPath = "https://image.tmdb.org/t/p/w300/"

fun getPosterUrl(url: String?): String {
    return if (url != null) {
        posterPath + url
    } else {
        ""
    }
}