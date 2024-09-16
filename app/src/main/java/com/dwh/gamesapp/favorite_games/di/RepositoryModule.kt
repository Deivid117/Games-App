package com.dwh.gamesapp.favorite_games.di

import com.dwh.gamesapp.favorite_games.data.repository.FavoriteGamesRepositoryImpl
import com.dwh.gamesapp.favorite_games.domain.repository.FavoriteGamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsFavoriteGamesRepository(favoriteGamesRepositoryImpl: FavoriteGamesRepositoryImpl): FavoriteGamesRepository
}