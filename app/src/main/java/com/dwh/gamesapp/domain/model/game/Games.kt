package com.dwh.gamesapp.domain.model.game

import com.dwh.gamesapp.data.model.response.games.GamesBodyResponse


data class Games(
    var gamesResults  : ArrayList<GamesResults> = arrayListOf()
)

fun GamesBodyResponse.toDomain() = Games(gamesResults = results.map { it.toDomain() } as ArrayList<GamesResults>)