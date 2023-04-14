package com.example.glartekchallenge.di

import com.example.glartekchallenge.data.domain.DefaultMovieRepository
import com.example.glartekchallenge.data.domain.MovieRepository
import com.example.glartekchallenge.data.remote.MoviesRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMovieRepository(
        remoteDataSource: MoviesRemoteSource
    ): MovieRepository {
        return DefaultMovieRepository(moviesRemoteSource = remoteDataSource)
    }
}
