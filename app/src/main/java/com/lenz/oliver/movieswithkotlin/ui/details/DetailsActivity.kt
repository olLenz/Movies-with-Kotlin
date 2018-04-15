package com.lenz.oliver.movieswithkotlin.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.View
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.ui.home.HomeActivity
import com.lenz.oliver.movieswithkotlin.utils.getBackdropUrl
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val detailsViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(DetailsViewModel::class.java)

        movie = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(HomeActivity.KEY_ITEM) as Movie
        } else {
            intent.getSerializableExtra(HomeActivity.KEY_ITEM) as Movie
        }

        setSupportActionBar(toolbar)

        detailsIv.transitionName = getString(R.string.transition_name_details) + movie?.id
        detailsIv.loadImage(getBackdropUrl(movie?.backdropPath))


        detailsViewModel.getMovieDetails(movie)
                ?.observe(this, Observer {
                    it?.let {
                        setMovieDetails(it)
                    }
                })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(HomeActivity.KEY_ITEM, movie)
        super.onSaveInstanceState(outState)
    }

    private fun setMovieDetails(movie: Movie) {
        detailsCtl.title = movie.title

        detailsDescriptionContainer.setOnClickListener({
            if (detailsDescriptionMoreTv.visibility == View.GONE) {
                detailsDescriptionMoreTv.visibility = View.VISIBLE
                detailsDescriptionTv.maxLines = 5
            } else {
                detailsDescriptionTv.maxLines = 1000
                detailsDescriptionMoreTv.visibility = View.GONE
            }
        })

        val castAdapter = CastAdapter(layoutInflater)
        detailsCastRv.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(this@DetailsActivity, HORIZONTAL, false)
            setHasFixedSize(true)
        }

        movie.credits?.cast?.let { cast ->
            castAdapter.setCast(cast)
        }
    }

}
