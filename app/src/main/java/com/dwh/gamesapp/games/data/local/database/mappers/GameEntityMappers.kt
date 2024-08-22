package com.dwh.gamesapp.games.data.local.database.mappers

import com.dwh.gamesapp.games.data.local.database.entities.GameEntity
import com.dwh.gamesapp.games.domain.model.Game

fun GameEntity.mapToDomain() = Game(
    id = id,
    slug = slug,
    name = name,
    released = released,
    backgroundImage = backgroundImage,
    metacritic = metacritic
)