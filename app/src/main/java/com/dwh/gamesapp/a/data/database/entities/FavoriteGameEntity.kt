package com.dwh.gamesapp.a.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame

@Entity(tableName = "favorite_games_table")
data class FavoriteGameEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo("name") var name : String,
    @ColumnInfo("released") var released : String,
    @ColumnInfo("background_image") var backgroundImage : String,
    @ColumnInfo("metacritic") var metacritic : Int,
)

fun FavoritGame.toDatabase() = FavoriteGameEntity(id, name, released, background_image, metacritic)
