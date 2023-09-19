package com.dwh.gamesapp.data.model.response.game_details

import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    @SerializedName("id"               ) var id              : Int = 0,
    @SerializedName("name"             ) var name            : String = "",
)
