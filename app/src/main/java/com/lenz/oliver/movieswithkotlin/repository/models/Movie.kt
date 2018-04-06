package com.lenz.oliver.movieswithkotlin.repository.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
        val id: Long?,
        @SerializedName("vote_count")
        val voteCount: Int?,
        @SerializedName("vote_average")
        val voteAverage: Double?,
        val title: String?,
        @SerializedName("release_date")
        val releaseDate: String?,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?
) : Serializable