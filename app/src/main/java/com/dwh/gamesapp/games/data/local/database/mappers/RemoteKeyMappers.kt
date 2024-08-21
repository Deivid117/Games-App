package com.dwh.gamesapp.games.data.local.database.mappers

import com.dwh.gamesapp.games.data.local.database.entities.RemoteKeyEntity
import com.dwh.gamesapp.games.domain.model.RemoteKey

fun RemoteKey.mapToDatabase() = RemoteKeyEntity(
    id = id,
    next_page = nextKey,
    last_updated = lastUpdated,
)
