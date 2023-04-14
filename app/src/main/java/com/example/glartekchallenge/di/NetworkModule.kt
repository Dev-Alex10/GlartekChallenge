package com.example.glartekchallenge.di

import com.example.glartekchallenge.data.remote.BASE_URL
import com.example.glartekchallenge.data.remote.CONNECTION_TIMEOUT_S
import com.example.glartekchallenge.data.remote.MoviesAPI
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMovies(): MoviesAPI {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(
                        CONNECTION_TIMEOUT_S,
                        TimeUnit.SECONDS
                    ).build()
            )
            .build()
            .create(MoviesAPI::class.java)
    }
}
