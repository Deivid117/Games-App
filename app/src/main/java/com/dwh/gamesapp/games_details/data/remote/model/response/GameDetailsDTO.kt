package com.dwh.gamesapp.games_details.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GameDetailsDTO(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("name_original") var nameOriginal : String? = null,
    @SerializedName("description") var description : String? = null,
    @SerializedName("metacritic") var metacritic : Int? = null,
    @SerializedName("released") var released : String? = null,
    @SerializedName("background_image") var backgroundImage : String? = null,
    @SerializedName("background_image_additional") var backgroundImageAdditional : String? = null,
    @SerializedName("website") var website : String? = null,
    @SerializedName("parent_platforms") var parentPlatforms : ArrayList<PlatformsDTO> = arrayListOf(),
    @SerializedName("platforms") var platforms : ArrayList<PlatformsDTO> = arrayListOf(),
    @SerializedName("developers") var developers : ArrayList<DevelopersDTO> = arrayListOf(),
    @SerializedName("genres") var genres : ArrayList<GenresDTO> = arrayListOf(),
    @SerializedName("publishers") var publishers : ArrayList<PublishersDTO> = arrayListOf(),
    @SerializedName("esrb_rating") var esrbRatingResponse : EsrbRatingDTO? = EsrbRatingDTO(),
    @SerializedName("description_raw") var descriptionRaw : String? = null
)
