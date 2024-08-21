package com.dwh.gamesapp.games.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_table")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    val next_page: Int?,
    val last_updated: Long
)