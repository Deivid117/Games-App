package com.dwh.gamesapp.genres_details.di

import com.dwh.gamesapp.genres_details.data.repository.GenreDetailsRepositoryImpl
import com.dwh.gamesapp.genres_details.domain.repository.GenreDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideGenreDetailsRepository(genreDetailsRepositoryImpl: GenreDetailsRepositoryImpl): GenreDetailsRepository
}