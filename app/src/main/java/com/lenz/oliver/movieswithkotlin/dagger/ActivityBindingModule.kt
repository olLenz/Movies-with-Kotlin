package com.lenz.oliver.movieswithkotlin.dagger

import com.lenz.oliver.movieswithkotlin.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity

}