package com.dwh.gamesapp.platforms.domain.model

import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformGamesDTO

data class PlattformGames(
    var id: Int?,
    var slug: String?,
    var name: String?,
    var added: Int?
)

fun PlatformGamesDTO.toDomain() = PlattformGames(id, slug, name, added)
