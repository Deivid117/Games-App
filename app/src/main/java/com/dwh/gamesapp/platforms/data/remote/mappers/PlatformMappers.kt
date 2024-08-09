package com.dwh.gamesapp.platforms.data.remote.mappers

import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformDTO
import com.dwh.gamesapp.platforms.data.remote.model.response.PlatformGamesDTO
import com.dwh.gamesapp.platforms.domain.model.Platform
import com.dwh.gamesapp.platforms.domain.model.PlatformGames

fun PlatformDTO.mapToDomain() = Platform(
    id = id,
    name = name,
    slug = slug,
    gamesCount = gamesCount,
    imageBackground = imageBackground,
    games = games.map { it.mapToDomain() } as ArrayList<PlatformGames>
)

fun PlatformGamesDTO.mapToDomain() = PlatformGames(
    id = id,
    slug = slug,
    name = name,
    added = added
)

