package com.lenz.oliver.movieswithkotlin.repository.models

enum class VideoTypes {
    Trailer, Teaser, Clip, Featurette
}

data class Video(
        val key: String? = null,
        val type: String? = null,
        val site: String? = null
)