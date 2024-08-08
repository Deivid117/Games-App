package com.dwh.gamesapp.games.domain.model

import com.dwh.gamesapp.games.data.model.GamesResultDTO


data class GamesResults(
    var results  : ArrayList<Game> = arrayListOf()
)

fun GamesResultDTO.toDomain() = GamesResults(results = results.map { it.toDomain() } as ArrayList<Game>)