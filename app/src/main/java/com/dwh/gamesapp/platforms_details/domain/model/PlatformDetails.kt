package com.dwh.gamesapp.platforms_details.domain.model

data class PlatformDetails(
    var id : Int? = null,
    var name : String? = null,
    var slug : String? = null,
    var gamesCount : Int? = null,
    var imageBackground : String? = null,
    var description : String? = null,
)