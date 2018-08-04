package com.lenz.oliver.movieswithkotlin.repository.interceptors

import com.lenz.oliver.movieswithkotlin.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

private const val API_KEY = "api_key"
private const val LANUAGE = "language"

class AuthInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request
                .url()
                .newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.ApiKey)
                .addQueryParameter(LANUAGE, Locale.getDefault().language)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}