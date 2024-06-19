package com.dwh.gamesapp.a.data.model.response.genres

import com.google.gson.annotations.SerializedName

data class GenresBodyResponse(
    @SerializedName("results"  ) var results  : ArrayList<GenreResultsResponse> = arrayListOf()
)
