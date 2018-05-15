package com.lenz.oliver.movieswithkotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.lenz.oliver.movieswithkotlin.dagger.ApplicationComponent
import com.lenz.oliver.movieswithkotlin.dagger.DaggerApplicationComponent
import com.lenz.oliver.movieswithkotlin.dagger.RepositoryModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject
import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.support.HasSupportFragmentInjector


/**
 * The basic application class
 */
class MoviesApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initLogging()

        initDagger()
    }

    override fun activityInjector() = dispatchingAndroidInjector

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //enable crash reporting
            Timber.plant(CrashReportingTree())
        }
    }

    /**
     * Initialise dagger components
     */
    private fun initDagger() {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        // automatically inject into activities
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                AndroidInjection.inject(activity)
            }

            override fun onActivityStarted(activity: Activity?) {}

            override fun onActivityResumed(activity: Activity?) {}

            override fun onActivityPaused(activity: Activity?) {}

            override fun onActivityStopped(activity: Activity?) {}

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

            override fun onActivityDestroyed(activity: Activity?) {}
        })
    }

    /**
     *  A tree which logs important information for crash reporting
     *  e.g. with Googles Crashlytics
     */
    private class CrashReportingTree : Timber.Tree() {

        override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {

            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return
            }

        }
    }
}