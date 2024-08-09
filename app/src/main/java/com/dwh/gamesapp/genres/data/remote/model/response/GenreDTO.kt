package com.dwh.gamesapp.genres.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("games_count") var gamesCount : Int? = null,
    @SerializedName("image_background") var imageBackground : String? = null,
    @SerializedName("games") var games : ArrayList<GenreGameDTO> = arrayListOf()
)
