package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.a.data.model.response.games.NextWeekGamesResponse

data class NextWeekGamesResults(
    var results  : ArrayList<NextWeekGames> = arrayListOf()
)

fun NextWeekGamesResponse.toDomain() = NextWeekGamesResults(results = results.map { it.toDomain() } as ArrayList<NextWeekGames>)