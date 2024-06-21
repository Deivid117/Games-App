package com.dwh.gamesapp.games_details.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class EsrbRatingDTO(
    @SerializedName("id"   ) var id   : Int? = null,
    @SerializedName("name" ) var name : String? = null,
)
