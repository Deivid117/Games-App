package com.dwh.gamesapp.di

import com.dwh.gamesapp.data.api.ApiConstants
import com.dwh.gamesapp.data.api.ApiService
import com.dwh.gamesapp.data.api.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun getRetrofit(): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create(ApiService::class.java)
    }

    private fun getClient(): OkHttpClient =
         OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .build()

}