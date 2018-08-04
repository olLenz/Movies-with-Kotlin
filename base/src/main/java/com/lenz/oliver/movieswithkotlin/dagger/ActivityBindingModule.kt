package com.lenz.oliver.movieswithkotlin.dagger

import com.lenz.oliver.movieswithkotlin.ui.details.DetailsActivity
import com.lenz.oliver.movieswithkotlin.ui.home.HomeActivity
import com.lenz.oliver.movieswithkotlin.ui.recommendations.RecommendationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun recommendationsActivity(): RecommendationActivity

    @ContributesAndroidInjector
    abstract fun detailsActivity(): DetailsActivity

}