package com.dwh.gamesapp.genres.di

import com.dwh.gamesapp.genres.data.repository.GenresRepositoryImpl
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository
}