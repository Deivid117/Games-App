package com.dwh.gamesapp.games_details.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class PlatformsDTO(
    @SerializedName("platform"     ) var platform     : PlatformDTO = PlatformDTO(),
)
