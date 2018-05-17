package com.lenz.oliver.movieswithkotlin.repository.models.pages

import com.lenz.oliver.movieswithkotlin.repository.models.Video
import com.lenz.oliver.movieswithkotlin.repository.models.VideoTypes

data class VideosPage(
        val results: List<Video>? = null
) {

    fun getYoutubeTrailer(): Video? {
        results?.forEach {video ->
            if (video.type.equals(VideoTypes.Trailer.name) && video.site.equals("YouTube")) {
                return video
            }
        }
        return null
    }

}