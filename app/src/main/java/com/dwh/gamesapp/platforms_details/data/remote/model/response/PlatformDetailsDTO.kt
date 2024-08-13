package com.dwh.gamesapp.platforms_details.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class PlatformDetailsDTO(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("slug") var slug : String? = null,
    @SerializedName("games_count") var gamesCount : Int? = null,
    @SerializedName("image_background") var imageBackground : String? = null,
    @SerializedName("description") var description : String? = null,
)
