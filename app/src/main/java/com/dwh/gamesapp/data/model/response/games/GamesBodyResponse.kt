package com.dwh.gamesapp.data.model.response.games

import com.google.gson.annotations.SerializedName

data class GamesBodyResponse(
    @SerializedName("count"    ) var count    : Int,
    @SerializedName("results"  ) var results  : ArrayList<GamesResultsResponse> = arrayListOf()
)
