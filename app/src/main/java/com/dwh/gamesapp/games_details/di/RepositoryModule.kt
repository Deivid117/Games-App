package com.dwh.gamesapp.games_details.di

import com.dwh.gamesapp.games_details.data.repository.GameDetailsRepositoryImpl
import com.dwh.gamesapp.games_details.domain.repository.GameDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideGameDetailsRepository(gameDetailsRepositoryImpl: GameDetailsRepositoryImpl): GameDetailsRepository
}