package com.dwh.gamesapp.a.domain.model.plattform

import com.dwh.gamesapp.a.data.model.response.plattforms.PlatformGamesResponse

data class PlattformGames(
    var id    : Int,
    var slug  : String,
    var name  : String,
    var added : Int
)

fun PlatformGamesResponse.toDomain() = PlattformGames(id, slug, name, added)
