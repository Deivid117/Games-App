package com.dwh.gamesapp.genres_details.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class GenreDetailsDTO(
    @SerializedName("id"               ) var id              : Int? = null,
    @SerializedName("name"             ) var name            : String? = null,
    @SerializedName("slug"             ) var slug            : String? = null,
    @SerializedName("games_count"      ) var gamesCount      : Int? = null,
    @SerializedName("image_background" ) var imageBackground : String? = null,
    @SerializedName("description"      ) var description     : String? = null,
    @SerializedName("description_raw"  ) var descriptionRaw            : String?                    = null
)
