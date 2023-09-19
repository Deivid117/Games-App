package com.dwh.gamesapp.data.model.response.genres

import com.google.gson.annotations.SerializedName

data class GenreGamesResponse(
    @SerializedName("id"    ) var id    : Int,
    @SerializedName("slug"  ) var slug  : String,
    @SerializedName("name"  ) var name  : String,
    @SerializedName("added" ) var added : Int
)
