package com.dwh.gamesapp.games_details.domain.model

data class GameDetails(
    var id : Int? = null,
    var nameOriginal : String? = null,
    var description : String? = null,
    var metacritic : Int? = null,
    var released : String? = null,
    var backgroundImage : String? = null,
    var backgroundImageAdditional : String? = null,
    var website : String? = null,
    var parentPlatforms : ArrayList<Platforms> = arrayListOf(),
    var platforms : ArrayList<Platforms> = arrayListOf(),
    var developers : ArrayList<Developers> = arrayListOf(),
    var genres : ArrayList<Genres> = arrayListOf(),
    var publishers : ArrayList<Publishers> = arrayListOf(),
    var esrbRatingResponse : EsrbRating? = EsrbRating(),
    var descriptionRaw : String? = null
)