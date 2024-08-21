package com.dwh.gamesapp.games.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwh.gamesapp.games.domain.model.Game

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("slug") var slug : String,
    @ColumnInfo("name") var name : String,
    @ColumnInfo("released") var released : String,
    @ColumnInfo("background_image") var backgroundImage : String,
    @ColumnInfo("metacritic") var metacritic : Int,
)

