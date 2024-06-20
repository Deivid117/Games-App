package com.dwh.gamesapp.platforms.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class PlatformDTO(
    @SerializedName("id"               ) var id              : Int? = null,
    @SerializedName("name"             ) var name            : String? = null,
    @SerializedName("slug"             ) var slug            : String? = null,
    @SerializedName("games_count"      ) var gamesCount      : Int? = null,
    @SerializedName("image_background" ) var imageBackground : String? = null,
    @SerializedName("games"            ) var games           : ArrayList<PlatformGamesDTO> = arrayListOf()
)
