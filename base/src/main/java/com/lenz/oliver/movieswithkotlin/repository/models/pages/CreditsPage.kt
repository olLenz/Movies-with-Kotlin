package com.lenz.oliver.movieswithkotlin.repository.models.pages

import com.lenz.oliver.movieswithkotlin.repository.models.Cast

data class CreditsPage(
        val cast: List<Cast>?
)