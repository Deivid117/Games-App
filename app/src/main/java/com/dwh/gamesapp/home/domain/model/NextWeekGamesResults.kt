package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.home.data.remote.model.response.NextWeekGamesResultsDTO

data class NextWeekGamesResults(
    var results: ArrayList<NextWeekGames> = arrayListOf()
)

fun NextWeekGamesResultsDTO.toDomain() = NextWeekGamesResults(results = results.map { it.toDomain() } as ArrayList<NextWeekGames>)