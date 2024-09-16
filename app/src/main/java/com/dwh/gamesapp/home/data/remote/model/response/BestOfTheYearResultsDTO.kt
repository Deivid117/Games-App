package com.dwh.gamesapp.home.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class BestOfTheYearResultsDTO(
    @SerializedName("results") var results : ArrayList<BestOfTheYearDTO> = arrayListOf()
)




