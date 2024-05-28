package com.dwh.gamesapp.data.model.response.games

import com.google.gson.annotations.SerializedName

data class NextWeekGamesResponse(
    @SerializedName("count"          ) var count         : Int?               = null,
    @SerializedName("results"        ) var results       : ArrayList<NextWeekGamesResultsResponse> = arrayListOf(),
)
