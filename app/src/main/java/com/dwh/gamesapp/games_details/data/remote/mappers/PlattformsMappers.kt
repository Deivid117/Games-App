package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.PlatformsDTO
import com.dwh.gamesapp.games_details.domain.model.Platforms

fun PlatformsDTO.mapToDomain() = Platforms(platform = platform.mapToDomain())