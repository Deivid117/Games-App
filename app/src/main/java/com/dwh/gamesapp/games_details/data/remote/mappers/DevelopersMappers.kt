package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.DevelopersDTO
import com.dwh.gamesapp.games_details.domain.model.Developers

fun DevelopersDTO.mapToDomain() = Developers(
    id = id,
    name = name
)