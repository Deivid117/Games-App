package com.dwh.gamesapp.home.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class BestOfTheYearDTO(
    @SerializedName("name"               ) var name             : String? = null,
    @SerializedName("released"           ) var released         : String? = null,
    @SerializedName("background_image"   ) var backgroundImage  : String? = null,
    @SerializedName("id"                 ) var id               : Int? = null
)