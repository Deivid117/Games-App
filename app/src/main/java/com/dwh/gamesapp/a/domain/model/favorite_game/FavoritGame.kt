package com.dwh.gamesapp.a.domain.model.favorite_game

import com.dwh.gamesapp.a.data.database.entities.FavoriteGameEntity

data class FavoritGame(
    var id : Int = 0,
    var name : String = "",
    var released : String = "",
    var background_image : String = "",
    var metacritic: Int = 0
)

fun FavoriteGameEntity.toDomain() = FavoritGame(id, name, released, backgroundImage, metacritic)