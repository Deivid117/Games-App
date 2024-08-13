package com.dwh.gamesapp.platforms_details.data.remote.mappers

import com.dwh.gamesapp.platforms_details.data.remote.model.response.PlatformDetailsDTO
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails

fun PlatformDetailsDTO.mapToDomain() = PlatformDetails(
    id = id,
    name = name,
    slug = slug,
    gamesCount = gamesCount,
    imageBackground = imageBackground,
    description = description
)
