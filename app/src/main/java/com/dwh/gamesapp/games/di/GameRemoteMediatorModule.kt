package com.dwh.gamesapp.games.di

import com.dwh.gamesapp.core.data.local.database.GameDatabase
import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.games.data.local.database.dao.GameDao
import com.dwh.gamesapp.games.data.local.database.dao.RemoteKeyDao
import com.dwh.gamesapp.games.data.repository.remote_mediator.GameRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GameRemoteMediatorModule {
     @Singleton
     @Provides
     fun provideGameRemoteMediator(
         gameDatabase: GameDatabase,
         gameApiService: GameApiService
     ): GameRemoteMediator {
         return GameRemoteMediator(
             gameDatabase = gameDatabase,
             gameApiService = gameApiService
         )
     }
}