package com.lenz.oliver.movieswithkotlin.repository.models

import android.support.annotation.StringRes

enum class Status {
    SUCCESS, ERROR, LOADING
}

/**
 * A generic class that holds a value with its loading status.
 * @param </T> */
data class Resource<out T>(val status: Status, val data: T?, @StringRes val message: Int?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(@StringRes msg: Int, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

}