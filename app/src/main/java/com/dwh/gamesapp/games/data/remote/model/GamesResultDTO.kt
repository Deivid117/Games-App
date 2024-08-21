package com.dwh.gamesapp.games.data.remote.model

import com.google.gson.annotations.SerializedName

data class GamesResultDTO(
    @SerializedName("results") var results  : ArrayList<GameDTO> = arrayListOf()
)
