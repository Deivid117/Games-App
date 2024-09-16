package com.dwh.gamesapp.favorite_games.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_games_table")
data class FavoriteGameEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo("name") var name : String,
    @ColumnInfo("released") var released : String,
    @ColumnInfo("background_image") var backgroundImage : String,
    @ColumnInfo("metacritic") var metacritic : Int
)