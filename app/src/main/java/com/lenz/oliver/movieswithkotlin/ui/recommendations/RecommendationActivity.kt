package com.lenz.oliver.movieswithkotlin.ui.recommendations

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.ui.home.HomeActivity.Companion.KEY_ITEM
import com.lenz.oliver.movieswithkotlin.utils.getPosterUrl
import kotlinx.android.synthetic.main.activity_recommendation.*

class RecommendationActivity : AppCompatActivity() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        movie = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(KEY_ITEM) as Movie
        } else {
            intent.getSerializableExtra(KEY_ITEM) as Movie
        }

        recommendationItemIv.loadImage(getPosterUrl(movie?.posterPath))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_ITEM, movie)
        super.onSaveInstanceState(outState)
    }
}
