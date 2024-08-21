package com.dwh.gamesapp.games.data.local.database.mappers

import com.dwh.gamesapp.games.data.local.database.entities.GameEntity
import com.dwh.gamesapp.games.domain.model.Game

fun Game.mapToDatabase() = GameEntity(
    id = id!!,
    slug = slug!!,
    name = name!!,
    released = released!!,
    backgroundImage = backgroundImage!!,
    metacritic = metacritic!!
)
