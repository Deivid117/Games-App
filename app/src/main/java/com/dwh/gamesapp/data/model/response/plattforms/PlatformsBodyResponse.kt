package com.dwh.gamesapp.data.model.response.plattforms

import com.google.gson.annotations.SerializedName

data class PlatformsBodyResponse(
    @SerializedName("results"  ) var results  : ArrayList<PlatformResultsResponse> = arrayListOf()
)
