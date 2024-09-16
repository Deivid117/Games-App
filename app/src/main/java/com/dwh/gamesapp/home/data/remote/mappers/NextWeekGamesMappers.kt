package com.dwh.gamesapp.home.data.remote.mappers

import com.dwh.gamesapp.home.data.remote.model.response.NextWeekGamesDTO
import com.dwh.gamesapp.home.domain.model.NextWeekGames

fun NextWeekGamesDTO.mapToDomain() = NextWeekGames(
    name = name,
    released = released,
    backgroundImage = backgroundImage,
    id = id
)
