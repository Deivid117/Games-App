package com.dwh.gamesapp.a.data.model.response.plattforms

import com.google.gson.annotations.SerializedName

data class PlatformGamesResponse(
    @SerializedName("id"    ) var id    : Int,
    @SerializedName("slug"  ) var slug  : String,
    @SerializedName("name"  ) var name  : String,
    @SerializedName("added" ) var added : Int
)
