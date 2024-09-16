package com.dwh.gamesapp.home.data.remote.mappers

import com.dwh.gamesapp.home.data.remote.model.response.BestOfTheYearDTO
import com.dwh.gamesapp.home.domain.model.BestOfTheYear

fun BestOfTheYearDTO.mapToDomain() = BestOfTheYear(
    name = name,
    released = released,
    backgroundImage = backgroundImage,
    id = id
)
