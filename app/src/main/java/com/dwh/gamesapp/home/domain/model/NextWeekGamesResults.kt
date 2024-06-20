package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.a.data.model.response.games.NextWeekGamesResultsDTO

data class NextWeekGamesResults(
    var results: ArrayList<NextWeekGames> = arrayListOf()
)

fun NextWeekGamesResultsDTO.toDomain() = NextWeekGamesResults(results = results.map { it.toDomain() } as ArrayList<NextWeekGames>)