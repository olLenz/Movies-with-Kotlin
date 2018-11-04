package com.lenz.oliver.movieswithkotlin.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.lenz.oliver.movieswithkotlin.Key
import com.lenz.oliver.movieswithkotlin.base.R
import com.lenz.oliver.movieswithkotlin.loadImage
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.repository.models.Status
import com.lenz.oliver.movieswithkotlin.showSnackbar
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
            savedInstanceState.getSerializable(Key.MOVIE) as Movie
        } else {
            intent.getSerializableExtra(Key.MOVIE) as Movie
        }

        setSupportActionBar(detailsToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            title = ""
        }

        detailsIv.transitionName = getString(R.string.transition_name_details) + movie?.id
        detailsIv.loadImage(getBackdropUrl(movie?.backdropPath))

        setMovieDetails(movie)

        detailsViewModel.getMovieDetails(movie)
                ?.observe(this, Observer {
                    when (it?.status) {
                        Status.SUCCESS -> {
                            setMovieDetails(it.data)
                        }
                        Status.ERROR -> {
                            detailsRv.showSnackbar(it.message)
                        }
                    }
                })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(Key.MOVIE, movie)
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

    override fun onBackPressed() {
        finish()
    }

    private fun setMovieDetails(movie: Movie?) {
        val trailer = movie?.videos?.getYoutubeTrailer()
        if (trailer != null) {
            detailsPlayIv.visibility = View.VISIBLE
            detailsToolbarContainer.setOnClickListener {
                val url = Uri.parse("https://www.youtube.com/watch?v=${trailer.key}")
                startActivity(
                        Intent(Intent.ACTION_VIEW, url)
                )
            }
        } else {
            detailsPlayIv.visibility = View.GONE
        }

        if (detailsAdapter == null) {
            detailsAdapter = DetailsAdapter(layoutInflater)
            detailsRv.layoutManager = LinearLayoutManager(this)
            detailsRv.adapter = detailsAdapter
        }

        detailsAdapter?.setMovie(movie)
    }

}
