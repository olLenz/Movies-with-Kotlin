package com.lenz.oliver.movieswithkotlin.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.Target
import com.lenz.oliver.movieswithkotlin.navigateTo
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

private const val SPAN_COUNT = 2

class HomeActivity : AppCompatActivity(), HomeAdapter.OnInteractionListener {

    companion object {
        const val KEY_ITEM = "key_item"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var homeAdapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeAdapter = HomeAdapter(LayoutInflater.from(this), this)

        homeRv.apply {
            // improve performance, because all items have the same size
            setHasFixedSize(true)

            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = homeAdapter
        }

        val homeViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        homeViewModel.getMoviesLiveData()
                ?.observe(this, Observer<List<Movie>> {
                    it?.let {
                        homeAdapter?.setMovies(it)
                    }
                })
    }

    override fun onItemClicked(movie: Movie, sharedElement: View) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_ITEM, movie)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, sharedElement, sharedElement.transitionName
        )

        navigateTo(this, Target.RECOMMENDATIONS, bundle, options.toBundle())
    }
}
