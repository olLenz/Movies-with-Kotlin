package com.lenz.oliver.movieswithkotlin.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.ui.home.HomeActivity
import com.lenz.oliver.movieswithkotlin.utils.clearLightStatusBar
import com.lenz.oliver.movieswithkotlin.utils.getBackdropUrl
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var movie: Movie? = null
    private var detailsAdapter: DetailsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_grey)
        clearLightStatusBar(window.decorView)

        val detailsViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(DetailsViewModel::class.java)

        movie = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(HomeActivity.KEY_ITEM) as Movie
        } else {
            intent.getSerializableExtra(HomeActivity.KEY_ITEM) as Movie
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.title = ""

        detailsIv.transitionName = getString(R.string.transition_name_details) + movie?.id
        detailsIv.loadImage(getBackdropUrl(movie?.backdropPath))

        setMovieDetails(movie)

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setMovieDetails(movie: Movie?) {
        if (detailsAdapter == null) {
            detailsAdapter = DetailsAdapter(layoutInflater)
            detailsRv.layoutManager = LinearLayoutManager(this)
            detailsRv.adapter = detailsAdapter
        }

        detailsAdapter?.setMovie(movie)
    }

}
