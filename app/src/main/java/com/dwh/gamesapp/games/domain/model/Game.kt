package com.dwh.gamesapp.games.domain.model

import com.dwh.gamesapp.games.data.local.database.entities.GameEntity

data class Game(
    var id : Int?,
    var slug : String?,
    var name : String?,
    var released : String?,
    var backgroundImage : String?,
    var metacritic: Int?
)

fun GameEntity.toDomain() = Game(id = id, slug = slug, name = name, released = released, backgroundImage = backgroundImage, metacritic = metacritic)
