package com.dwh.gamesapp.games.domain.model

data class RemoteKey(
    val id: String,
    val nextKey: Int?,
    val lastUpdated: Long,
)
