package com.dwh.gamesapp.games.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameDTO(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("slug") var slug : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("released") var released : String? = null,
    @SerializedName("background_image") var backgroundImage : String? = null,
    @SerializedName("metacritic") var metacritic : Int? = null
)
