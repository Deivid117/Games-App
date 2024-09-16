package com.dwh.gamesapp.home.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class NextWeekGamesResultsDTO(
    @SerializedName("results") var results : ArrayList<NextWeekGamesDTO> = arrayListOf()
)
