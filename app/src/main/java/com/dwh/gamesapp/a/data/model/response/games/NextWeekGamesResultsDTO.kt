package com.dwh.gamesapp.a.data.model.response.games

import com.google.gson.annotations.SerializedName

data class NextWeekGamesResultsDTO(
    @SerializedName("results"        ) var results       : ArrayList<NextWeekGamesDTO> = arrayListOf(),
)
