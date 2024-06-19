package com.dwh.gamesapp.a.domain.model.game_details

import com.dwh.gamesapp.a.data.model.response.game_details.GameDetailsResponse

data class GameDetails(
    var id : Int,
    var nameOriginal : String,
    var description : String,
    var metacritic : Int,
    var released : String,
    var backgroundImage : String,
    var backgroundImageAdditional : String,
    var website : String,
    var platforms : ArrayList<Platfforms> = arrayListOf(),
    var developers : ArrayList<Developers> = arrayListOf(),
    var genres : ArrayList<Genres> = arrayListOf(),
    var publishers : ArrayList<Publishers> = arrayListOf(),
    var esrbRatingResponse : EsrbRating? = EsrbRating(),
    var descriptionRaw : String
)

fun GameDetailsResponse.toDomain() = GameDetails(
    id = id,
    nameOriginal,
    description,
    metacritic,
    released,
    backgroundImage,
    backgroundImageAdditional,
    website,
    platforms = platforms.map { it.toDomain() } as ArrayList<Platfforms>,
    developers = developers.map { it.toDomain() } as ArrayList<Developers>,
    genres = genres.map { it.toDomain() } as ArrayList<Genres>,
    publishers = publishers.map { it.toDomain() } as ArrayList<Publishers>,
    esrbRatingResponse = esrbRatingResponse?.toDomain(),
    descriptionRaw
)
