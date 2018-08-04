package com.lenz.oliver.movieswithkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lenz.oliver.movieswithkotlin.ui.details.DetailsActivity
import com.lenz.oliver.movieswithkotlin.ui.recommendations.RecommendationActivity
import timber.log.Timber

enum class Target {
    RECOMMENDATIONS, DETAILS
}

/**
 * Navigates to the given [target] by starting the corresponding activity [Context]
 */
fun navigateTo(context: Context?, target: Target, bundle: Bundle? = null,
               activityOptions: Bundle? = null) {
    if (context == null) {
        Timber.e("Error in navigateTo: Context is null")
        return
    }
    val intent = when (target) {
        Target.RECOMMENDATIONS -> Intent(context, RecommendationActivity::class.java)
        Target.DETAILS -> Intent(context, DetailsActivity::class.java)
    }

    bundle?.let {
        intent.putExtras(it)
    }

    context.startActivity(intent, activityOptions)
}