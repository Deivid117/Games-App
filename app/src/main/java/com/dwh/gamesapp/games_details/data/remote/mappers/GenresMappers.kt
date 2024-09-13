package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.GenresDTO
import com.dwh.gamesapp.games_details.domain.model.Genres

fun GenresDTO.mapToDomain() = Genres(
    id = id,
    name = name,
    imageBackground = imageBackground
)