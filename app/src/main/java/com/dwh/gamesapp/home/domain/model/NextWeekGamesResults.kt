package com.dwh.gamesapp.home.domain.model

import com.dwh.gamesapp.home.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.home.data.remote.model.response.NextWeekGamesResultsDTO

data class NextWeekGamesResults(
    var results: ArrayList<NextWeekGames> = arrayListOf()
)

fun NextWeekGamesResultsDTO.toDomain() = NextWeekGamesResults(results = results.map { it.mapToDomain() } as ArrayList<NextWeekGames>)