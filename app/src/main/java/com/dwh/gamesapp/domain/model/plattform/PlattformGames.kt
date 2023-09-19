package com.dwh.gamesapp.domain.model.plattform

import com.dwh.gamesapp.data.model.response.plattforms.PlatformGamesResponse

data class PlattformGames(
    var id    : Int,
    var slug  : String,
    var name  : String,
    var added : Int
)

fun PlatformGamesResponse.toDomain() = PlattformGames(id, slug, name, added)
