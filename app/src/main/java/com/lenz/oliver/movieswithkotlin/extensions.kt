package com.lenz.oliver.movieswithkotlin

import android.widget.ImageView
import com.bumptech.glide.request.target.Target

fun ImageView.loadImage(imageUrl: String) {
    GlideApp.with(context)
            .load(imageUrl)
            .override(Target.SIZE_ORIGINAL)
            .into(this)
}