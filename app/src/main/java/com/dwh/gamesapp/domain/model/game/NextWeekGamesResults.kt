package com.dwh.gamesapp.domain.model.game

import com.dwh.gamesapp.data.model.response.games.NextWeekGamesResponse
import com.dwh.gamesapp.data.model.response.games.NextWeekGamesResultsResponse
import com.google.gson.annotations.SerializedName

data class NextWeekGamesResults(
    var name             : String,
    var released         : String,
    var backgroundImage  : String,
    var id               : Int
)

fun NextWeekGamesResultsResponse.toDomain() = NextWeekGamesResults(name, released, backgroundImage, id)
