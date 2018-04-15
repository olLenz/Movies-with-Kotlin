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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.Target
import com.lenz.oliver.movieswithkotlin.navigateTo
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject
import com.lenz.oliver.movieswithkotlin.R.id.bottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem


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

        setupBottomBar()

        val homeViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        homeAdapter = HomeAdapter(LayoutInflater.from(this), this)

        homeRv.apply {
            // improve performance, because all items have the same size
            setHasFixedSize(true)

            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = homeAdapter
        }

        homeViewModel.getMoviesLiveData()
                ?.observe(this, Observer {
                    it?.let {
                        homeAdapter?.setMovies(it)
                    }
                })
    }

    private fun setupBottomBar() {
        // Create items
        val item1 = AHBottomNavigationItem(R.string.movies, R.drawable.ic_movie, R.color.bright_red)
        val item2 = AHBottomNavigationItem(R.string.series, R.drawable.ic_movie, R.color.bright_green)

        // Add items
        bottomNavigation.addItem(item1)
        bottomNavigation.addItem(item2)

        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.isColored = true
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
