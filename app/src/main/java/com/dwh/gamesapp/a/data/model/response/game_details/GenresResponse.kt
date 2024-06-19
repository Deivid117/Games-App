package com.dwh.gamesapp.a.data.model.response.game_details

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("id"               ) var id              : Int,
    @SerializedName("name"             ) var name            : String,
    @SerializedName("games_count"      ) var gamesCount      : Int,
    @SerializedName("image_background" ) var imageBackground : String,
)
