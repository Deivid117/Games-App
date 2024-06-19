package com.dwh.gamesapp.a.domain.model.game

import com.dwh.gamesapp.a.data.database.entities.GameEntity
import com.dwh.gamesapp.a.data.model.response.games.GamesResultsResponse
data class GamesResults(
    var id : Int,
    var slug : String,
    var name : String,
    var released : String,
    var backgroundImage : String,
    var metacritic: Int
)

fun GamesResultsResponse.toDomain() = GamesResults(id = id, slug = slug, name = name, released =  released, backgroundImage = backgroundImage, metacritic = metacritic)
fun GameEntity.toDomain() = GamesResults(id = id, slug = slug, name = name, released = released, backgroundImage = backgroundImage, metacritic = metacritic)
