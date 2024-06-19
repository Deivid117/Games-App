package com.dwh.gamesapp.core.data.remote.api

import com.dwh.gamesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    private val apiKey = BuildConfig.API_KEY
    private val paramKey = "key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val urlWithParams = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(paramKey, apiKey)
            .build()

        val request = chain.request().newBuilder()
            .url(urlWithParams)
            .addHeader("Content-Type", "application/json")
            .build()

        return  chain.proceed(request)
    }
}