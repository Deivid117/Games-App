package com.dwh.gamesapp.favorite_games.data.local.database.mappers

import com.dwh.gamesapp.favorite_games.data.local.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame

fun FavoriteGameEntity.mapToDomain() = FavoriteGame(
    id = id,
    name = name,
    released = released,
    background_image = backgroundImage,
    metacritic = metacritic
)

fun FavoriteGame.mapToDatabase() = FavoriteGameEntity(
    id = id,
    name = name,
    released = released,
    backgroundImage = background_image,
    metacritic = metacritic
)