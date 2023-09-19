package com.dwh.gamesapp.domain.model.plattform

import com.dwh.gamesapp.data.model.response.plattforms.PlatformResultsResponse

data class PlattformResults(
    var id              : Int,
    var name            : String,
    var slug            : String,
    var gamesCount      : Int,
    var imageBackground : String,
    var games           : ArrayList<PlattformGames> = arrayListOf()
)

fun PlatformResultsResponse.toDomain() = PlattformResults(id, name, slug, gamesCount, imageBackground, games.map { it.toDomain() } as ArrayList<PlattformGames>)