package com.dwh.gamesapp.data.model.response.game_details

import com.google.gson.annotations.SerializedName

data class GameDetailsResponse(
    @SerializedName("id"                          ) var id                        : Int,
    @SerializedName("name_original"               ) var nameOriginal              : String,
    @SerializedName("description"                 ) var description               : String,
    @SerializedName("metacritic"                  ) var metacritic                : Int,
    @SerializedName("released"                    ) var released                  : String,
    @SerializedName("background_image"            ) var backgroundImage           : String,
    @SerializedName("background_image_additional" ) var backgroundImageAdditional : String,
    @SerializedName("website"                     ) var website                   : String,
    @SerializedName("platforms"                   ) var platforms                 : ArrayList<PlatformsResponse> = arrayListOf(),
    @SerializedName("developers"                  ) var developers                : ArrayList<DevelopersResponse> = arrayListOf(),
    @SerializedName("genres"                      ) var genres                    : ArrayList<GenresResponse> = arrayListOf(),
    @SerializedName("publishers"                  ) var publishers                : ArrayList<PublishersResponse> = arrayListOf(),
    @SerializedName("esrb_rating"                 ) var esrbRatingResponse        : EsrbRatingResponse? = EsrbRatingResponse(),
    @SerializedName("description_raw"             ) var descriptionRaw            : String
)
