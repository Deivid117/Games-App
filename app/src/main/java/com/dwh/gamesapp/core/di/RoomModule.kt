package com.dwh.gamesapp.core.di

import android.content.Context
import com.dwh.gamesapp.a.data.database.DBGamesApp
import com.dwh.gamesapp.a.data.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.a.data.database.dao.GameDao
import com.dwh.gamesapp.a.data.database.dao.UserDao
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
    ): DBGamesApp {
        return DBGamesApp.newInstance(context)
    }

    @Singleton
    @Provides
    fun provideGameDao(dbGamesApp: DBGamesApp): GameDao {
        return dbGamesApp.gameDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(dbGamesApp: DBGamesApp): UserDao {
        return dbGamesApp.userDao()
    }

    @Singleton
    @Provides
    fun providesFavoriteGameDao(dbGamesApp: DBGamesApp): FavoriteGamesDao {
        return dbGamesApp.favoriteGameDao()
    }
}