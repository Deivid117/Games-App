package com.dwh.gamesapp.data.model.response.game_details

import com.google.gson.annotations.SerializedName

data class DevelopersResponse(
    @SerializedName("id"               ) var id              : Int,
    @SerializedName("name"             ) var name            : String,
)
