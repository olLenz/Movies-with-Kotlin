package com.lenz.oliver.movieswithkotlin.ui.recommendations

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.Target
import com.lenz.oliver.movieswithkotlin.navigateTo
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.ui.home.HomeActivity.Companion.KEY_ITEM
import kotlinx.android.synthetic.main.activity_recommendation.*
import javax.inject.Inject

class RecommendationActivity : AppCompatActivity(), RecommendationAdapter.OnInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var recommendationsAdapter: RecommendationAdapter? = null

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        val recommendationsViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(RecommendationsViewModel::class.java)

        movie = if (savedInstanceState != null) {
            savedInstanceState.getSerializable(KEY_ITEM) as Movie
        } else {
            intent.getSerializableExtra(KEY_ITEM) as Movie
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        toolbarTitle.text = movie?.title

        recommendationsAdapter = RecommendationAdapter(LayoutInflater.from(this), this)

        recommendationRv.apply {
            // improve performance, because all items have the same size
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)
            adapter = recommendationsAdapter
        }

        recommendationsViewModel.getRecommendationsLiveData(movie)
                ?.observe(this, Observer {
                    it?.let {
                        recommendationsAdapter?.setMovies(it)
                        recommendationRv.scheduleLayoutAnimation()
                    }
                })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_ITEM, movie)
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

    override fun onItemClicked(movie: Movie, sharedElement: View) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_ITEM, movie)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, sharedElement, sharedElement.transitionName
        )

        navigateTo(this, Target.DETAILS, bundle, options.toBundle())
    }
}
