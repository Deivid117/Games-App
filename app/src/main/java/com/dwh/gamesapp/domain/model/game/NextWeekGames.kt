package com.dwh.gamesapp.domain.model.game

import com.dwh.gamesapp.data.model.response.games.NextWeekGamesResponse

data class NextWeekGames(
    var nextWeekGamesResults  : ArrayList<NextWeekGamesResults> = arrayListOf()
)

fun NextWeekGamesResponse.toDomain() = NextWeekGames(nextWeekGamesResults = results.map { it.toDomain() } as ArrayList<NextWeekGamesResults>)