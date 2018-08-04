package com.lenz.oliver.movieswithkotlin.dagger

import android.arch.lifecycle.ViewModel
import com.lenz.oliver.movieswithkotlin.ui.home.HomeViewModel
import dagger.multibindings.IntoMap
import dagger.Binds
import android.arch.lifecycle.ViewModelProvider
import com.lenz.oliver.movieswithkotlin.ui.details.DetailsViewModel
import com.lenz.oliver.movieswithkotlin.ui.recommendations.RecommendationsViewModel
import dagger.Module
import dagger.multibindings.ClassKey

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecommendationsViewModel::class)
    abstract fun bindRecommendationsViewModel(recommendationsViewModel: RecommendationsViewModel)
            : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel)
            : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MovieViewModelFactory)
            : ViewModelProvider.Factory
}