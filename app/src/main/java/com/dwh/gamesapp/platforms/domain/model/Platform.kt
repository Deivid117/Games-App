package com.dwh.gamesapp.platforms.domain.model

import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformDTO
import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformGamesDTO

data class Platform(
    var id: Int?,
    var name: String?,
    var slug: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var games: ArrayList<PlatformGamesDTO> = arrayListOf()
)

fun PlatformDTO.toDomain() = Platform(id, name, slug, gamesCount, imageBackground, games)
