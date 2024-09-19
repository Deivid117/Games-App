package com.dwh.gamesapp.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.dwh.gamesapp.core.data.local.database.converter.ByteArrayConverter
import com.dwh.gamesapp.core.data.local.database.converter.EncryptedDataConverter
import com.dwh.gamesapp.favorite_games.data.local.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.games.data.local.database.dao.GameDao
import com.dwh.gamesapp.core.data.local.database.dao.UserDao
import com.dwh.gamesapp.favorite_games.data.local.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.games.data.local.database.entities.GameEntity
import com.dwh.gamesapp.core.data.local.database.entities.UserEntity
import com.dwh.gamesapp.games.data.local.database.dao.RemoteKeyDao
import com.dwh.gamesapp.games.data.local.database.entities.RemoteKeyEntity

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        RemoteKeyEntity::class,
        FavoriteGameEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ByteArrayConverter::class, EncryptedDataConverter::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun favoriteGameDao(): FavoriteGamesDao

    companion object {
        @JvmStatic
        fun newInstance(context: Context): GameDatabase {
            return Room
                .databaseBuilder(context, GameDatabase::class.java, "DBGamesApp")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}