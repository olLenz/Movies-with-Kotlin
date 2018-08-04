package com.lenz.oliver.movieswithkotlin.utils

private const val SECURE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val POSTER_PATH = "w300/"
private const val BACKDROP_PATH = "w780/"

fun getPosterUrl(url: String?): String {
    return if (url != null) {
        SECURE_BASE_URL + POSTER_PATH + url
    } else {
        ""
    }
}

fun getBackdropUrl(url: String?): String {
    return if (url != null) {
        SECURE_BASE_URL + BACKDROP_PATH + url
    } else {
        ""
    }
}