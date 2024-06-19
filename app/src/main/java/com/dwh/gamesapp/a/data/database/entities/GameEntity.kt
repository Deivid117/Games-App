package com.dwh.gamesapp.a.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwh.gamesapp.a.domain.model.game.GamesResults

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo("slug") var slug : String,
    @ColumnInfo("name") var name : String,
    @ColumnInfo("released") var released : String,
    @ColumnInfo("background_image") var backgroundImage : String,
    @ColumnInfo("metacritic") var metacritic : Int,
)

fun GamesResults.toDatabase() = GameEntity(id = id, slug = slug, name = name, released = released, backgroundImage = backgroundImage, metacritic = metacritic)
