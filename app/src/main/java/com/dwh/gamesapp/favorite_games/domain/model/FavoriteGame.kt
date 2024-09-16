package com.dwh.gamesapp.favorite_games.domain.model

data class FavoriteGame(
    var id : Int = 0,
    var name : String = "",
    var released : String = "",
    var background_image : String = "",
    var metacritic: Int = 0
)