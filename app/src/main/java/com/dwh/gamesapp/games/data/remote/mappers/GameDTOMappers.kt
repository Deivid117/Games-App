package com.dwh.gamesapp.games.data.remote.mappers

import com.dwh.gamesapp.games.data.local.database.entities.GameEntity
import com.dwh.gamesapp.games.data.remote.model.GameDTO
import com.dwh.gamesapp.games.domain.model.Game

fun GameDTO.mapToDomain() = Game(
    id = id,
    slug = slug,
    name = name,
    released = released,
    backgroundImage = backgroundImage,
    metacritic = metacritic
)

fun GameDTO.mapToEntity() = GameEntity(
    id = id ?: 0,
    slug = slug ?: "",
    name = name ?: "",
    released = released ?: "",
    backgroundImage = backgroundImage ?: "",
    metacritic = metacritic ?:0
)
