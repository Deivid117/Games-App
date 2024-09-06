package com.dwh.gamesapp.core.di

import android.content.Context
import com.dwh.gamesapp.core.data.local.database.GameDatabase
import com.dwh.gamesapp.a.data.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.games.data.local.database.dao.GameDao
import com.dwh.gamesapp.core.data.local.database.dao.UserDao
import com.dwh.gamesapp.games.data.local.database.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext
        context: Context
    ): GameDatabase {
        return GameDatabase.newInstance(context)
    }

    @Singleton
    @Provides
    fun provideGameDao(gameDatabase: GameDatabase): GameDao {
        return gameDatabase.gameDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeyDao(gameDatabase: GameDatabase): RemoteKeyDao {
        return gameDatabase.remoteKeyDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(gameDatabase: GameDatabase): UserDao {
        return gameDatabase.userDao()
    }

    @Singleton
    @Provides
    fun providesFavoriteGameDao(gameDatabase: GameDatabase): FavoriteGamesDao {
        return gameDatabase.favoriteGameDao()
    }
}