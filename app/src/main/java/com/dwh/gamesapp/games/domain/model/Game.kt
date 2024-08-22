package com.dwh.gamesapp.games.domain.model

data class Game(
    var id : Int?,
    var slug : String?,
    var name : String?,
    var released : String?,
    var backgroundImage : String?,
    var metacritic: Int?
)