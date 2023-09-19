package com.dwh.gamesapp.data.model.response.game_details

import com.google.gson.annotations.SerializedName

data class PlatformsResponse(
    @SerializedName("platform"     ) var platform     : PlatformResponse     = PlatformResponse(),
)
