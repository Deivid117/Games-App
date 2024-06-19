package com.dwh.gamesapp.a.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dwh.gamesapp.a.data.database.dao.FavoriteGamesDao
import com.dwh.gamesapp.a.data.database.dao.GameDao
import com.dwh.gamesapp.a.data.database.dao.UserDao
import com.dwh.gamesapp.a.data.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.a.data.database.entities.GameEntity
import com.dwh.gamesapp.a.data.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        GameEntity::class,
        FavoriteGameEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class DBGamesApp : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun favoriteGameDao(): FavoriteGamesDao

    companion object {
        @JvmStatic
        fun newInstance(context: Context): DBGamesApp {
            return Room
                .databaseBuilder(context, DBGamesApp::class.java, "DBGamesApp")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}