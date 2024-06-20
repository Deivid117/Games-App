package com.dwh.gamesapp.platforms.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class PlatformsResultsDTO(
    @SerializedName("results"  ) var results  : ArrayList<PlatformDTO> = arrayListOf()
)
