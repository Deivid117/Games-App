package com.dwh.gamesapp.games_details.data.remote.mappers

import com.dwh.gamesapp.games_details.data.remote.model.response.PublishersDTO
import com.dwh.gamesapp.games_details.domain.model.Publishers

fun PublishersDTO.mapToDomain() = Publishers(
    id = id,
    name = name
)