package com.dwh.gamesapp.data.model.response.genres

import com.google.gson.annotations.SerializedName

data class GenreResultsResponse(
    @SerializedName("id"               ) var id              : Int,
    @SerializedName("name"             ) var name            : String,
    @SerializedName("games_count"      ) var gamesCount      : Int,
    @SerializedName("image_background" ) var imageBackground : String,
    @SerializedName("games"            ) var games           : ArrayList<GenreGamesResponse> = arrayListOf()
)
