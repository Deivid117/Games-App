package com.dwh.gamesapp.games.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_table")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id") val id: String,
    @ColumnInfo("next_page") val nextPage: Int?,
    @ColumnInfo("last_updated") val lastUpdated: Long
)