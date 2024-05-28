package com.dwh.gamesapp.data.model.response.games

import com.google.gson.annotations.SerializedName

data class NextWeekGamesResultsResponse(
    @SerializedName("name"               ) var name             : String,
    @SerializedName("released"           ) var released         : String,
    @SerializedName("background_image"   ) var backgroundImage  : String,
    @SerializedName("id"                 ) var id               : Int
)
