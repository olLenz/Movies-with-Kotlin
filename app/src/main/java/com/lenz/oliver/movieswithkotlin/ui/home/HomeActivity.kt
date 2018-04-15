package com.lenz.oliver.movieswithkotlin.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.Target
import com.lenz.oliver.movieswithkotlin.navigateTo
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private const val SPAN_COUNT = 2
private const val SEARCH_DELAY = 500L

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


        // trigger search requests with delay
        createSearchObservable(movieSearchTv)
                .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (it.isEmpty()) {
                        homeViewModel.searchMovie(it)
                        false
                    } else {
                        true
                    }
                }
                .distinctUntilChanged()
                .subscribe {
                    homeViewModel.searchMovie(it)
                }
    }

    override fun onItemClicked(movie: Movie, sharedElement: View) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_ITEM, movie)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, sharedElement, sharedElement.transitionName
        )

        navigateTo(this, Target.RECOMMENDATIONS, bundle, options.toBundle())
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

    private fun createSearchObservable(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { subject.onNext(it) }
                return true
            }
        })

        return subject
    }
}
