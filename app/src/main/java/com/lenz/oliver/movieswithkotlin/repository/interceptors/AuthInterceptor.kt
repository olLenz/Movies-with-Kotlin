package com.lenz.oliver.movieswithkotlin.repository.interceptors

import com.lenz.oliver.movieswithkotlin.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "api_key"

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request
                .url()
                .newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.ApiKey)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}