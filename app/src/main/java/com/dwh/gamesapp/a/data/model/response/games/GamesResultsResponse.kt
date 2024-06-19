package com.dwh.gamesapp.a.data.model.response.games

import com.google.gson.annotations.SerializedName

data class GamesResultsResponse(
    @SerializedName("id"                 ) var id               : Int,
    @SerializedName("slug"               ) var slug             : String,
    @SerializedName("name"               ) var name             : String,
    @SerializedName("released"           ) var released         : String,
    @SerializedName("background_image"   ) var backgroundImage  : String,
    @SerializedName("metacritic"         ) var metacritic       : Int,
)
