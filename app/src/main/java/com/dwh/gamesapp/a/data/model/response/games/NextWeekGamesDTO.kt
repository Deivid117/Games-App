package com.dwh.gamesapp.a.data.model.response.games

import com.google.gson.annotations.SerializedName

data class NextWeekGamesDTO(
    @SerializedName("name"               ) var name             : String? = null,
    @SerializedName("released"           ) var released         : String? = null,
    @SerializedName("background_image"   ) var backgroundImage  : String? = null,
    @SerializedName("id"                 ) var id               : Int? = null
)
