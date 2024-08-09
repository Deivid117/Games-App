package com.dwh.gamesapp.games.di

import com.dwh.gamesapp.games.data.data_source.GamesDataSource
import com.dwh.gamesapp.games.data.data_source.GamesDataSourceImpl
import com.dwh.gamesapp.games.data.repository.GamesRepositoryImp
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsGamesRepository(gamesRepositoryImp: GamesRepositoryImp): GamesRepository

    @Binds
    abstract fun bindsGamesDataSource(gamesDataSourceImpl: GamesDataSourceImpl): GamesDataSource
}