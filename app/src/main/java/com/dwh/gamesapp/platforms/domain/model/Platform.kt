package com.dwh.gamesapp.platforms.domain.model

import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformDTO

data class Platform(
    var id: Int?,
    var name: String?,
    var slug: String?,
    var gamesCount: Int?,
    var imageBackground: String?,
    var games: ArrayList<PlattformGames> = arrayListOf()
)

fun PlatformDTO.toDomain() = Platform(id, name, slug, gamesCount, imageBackground, games.map { it.toDomain() } as ArrayList<PlattformGames>)
