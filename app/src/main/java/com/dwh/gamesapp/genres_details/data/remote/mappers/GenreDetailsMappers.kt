package com.dwh.gamesapp.genres_details.data.remote.mappers

import com.dwh.gamesapp.genres_details.data.remote.model.response.GenreDetailsDTO
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails

fun GenreDetailsDTO.mapToDomain() = GenreDetails(
    id = id,
    name = name,
    slug = slug,
    gamesCount = gamesCount,
    imageBackground = imageBackground,
    description = description,
    descriptionRaw = descriptionRaw
)
