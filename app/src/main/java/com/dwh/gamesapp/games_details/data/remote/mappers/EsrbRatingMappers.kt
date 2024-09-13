package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.EsrbRatingDTO
import com.dwh.gamesapp.games_details.domain.model.EsrbRating

fun EsrbRatingDTO.mapToDomain() = EsrbRating(
    id = id,
    name = name
)