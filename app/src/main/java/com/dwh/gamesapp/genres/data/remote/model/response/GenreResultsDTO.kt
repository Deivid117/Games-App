package com.dwh.gamesapp.genres.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GenreResultsDTO(
    @SerializedName("results") var results : ArrayList<GenreDTO> = arrayListOf()
)
