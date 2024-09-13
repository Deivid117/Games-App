package com.dwh.gamesapp.games_details.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GenresDTO(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("games_count") var gamesCount : Int? = null,
    @SerializedName("image_background") var imageBackground : String? = null
)
