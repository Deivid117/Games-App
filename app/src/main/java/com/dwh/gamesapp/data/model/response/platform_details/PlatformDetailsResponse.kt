package com.dwh.gamesapp.data.model.response.platform_details

import com.google.gson.annotations.SerializedName

data class PlatformDetailsResponse(
    @SerializedName("id"               ) var id              : Int,
    @SerializedName("name"             ) var name            : String,
    @SerializedName("slug"             ) var slug            : String,
    @SerializedName("games_count"      ) var gamesCount      : Int,
    @SerializedName("image_background" ) var imageBackground : String,
    @SerializedName("description"      ) var description     : String,
)
