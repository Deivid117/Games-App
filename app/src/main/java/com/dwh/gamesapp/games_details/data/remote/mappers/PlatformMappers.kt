package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.PlatformDTO
import com.dwh.gamesapp.games_details.domain.model.Platform

fun PlatformDTO.mapToDomain() = Platform(
    id = id,
    name = name
)