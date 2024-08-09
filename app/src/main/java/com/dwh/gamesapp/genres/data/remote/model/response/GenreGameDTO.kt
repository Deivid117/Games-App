package com.dwh.gamesapp.genres.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GenreGameDTO(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("slug") var slug : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("added") var added : Int? = null
)
