package com.lenz.oliver.movieswithkotlin.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.lenz.oliver.movieswithkotlin.Key
import com.lenz.oliver.movieswithkotlin.R
import com.lenz.oliver.movieswithkotlin.Target
import com.lenz.oliver.movieswithkotlin.navigateTo
import com.lenz.oliver.movieswithkotlin.repository.models.Movie
import com.lenz.oliver.movieswithkotlin.utils.hideKeyboard
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private const val SPAN_COUNT = 2
private const val SEARCH_DELAY = 500L

class HomeActivity : AppCompatActivity(), HomeAdapter.OnInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var homeAdapter: HomeAdapter? = null
    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(homeToolbar)

        homeViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        homeAdapter = HomeAdapter(layoutInflater, this)

        homeRv.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            adapter = homeAdapter

            //hide search keyboard when scrolling
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    hideKeyboard(this@HomeActivity)
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }

        homeViewModel?.getMoviesLiveData()
                ?.observe(this, Observer {
                    it?.let {
                        homePb.visibility = View.GONE
                        homeAdapter?.setMovies(it)
                        homeRv.scheduleLayoutAnimation()
                    }
                })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val searchMenuItem = menu.findItem(R.id.searchItem)
        val searchView = searchMenuItem.actionView as SearchView

        // trigger search requests with delay
        createSearchObservable(searchView)
                .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter {
                    if (it.isEmpty()) {
                        homeViewModel?.searchMovie(it)
                        false

                    } else {
                        true
                    }
                }
                .distinctUntilChanged()
                .subscribe {
                    homeViewModel?.searchMovie(it)
                }

        return true
    }

    override fun onItemClicked(movie: Movie) {
        val bundle = Bundle()
        bundle.putSerializable(Key.MOVIE, movie)
        navigateTo(this, Target.RECOMMENDATIONS, bundle)
    }

    private fun createSearchObservable(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { subject.onNext(it) }
                hideKeyboard(this@HomeActivity)
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
