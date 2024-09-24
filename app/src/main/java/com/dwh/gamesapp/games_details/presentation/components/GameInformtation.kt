package com.dwh.gamesapp.games_details.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.composables.details.DescriptionDetails
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.games_details.domain.model.GameDetails

@Composable
fun GameInformation(
    scrollState: ScrollState,
    gameDetails: GameDetails?,
    isMyFavoriteGame: Boolean,
    addToFavorites: () -> Unit,
    deleteFromFavorites: () -> Unit
) {
    val favoriteIconColor = if (isMyFavoriteGame) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(modifier = Modifier.height(Constants.headerHeight - 50.dp))

        DateAndPlatforms(
            releaseDate = gameDetails?.released ?: "",
            parentPlatforms = gameDetails?.parentPlatforms ?: arrayListOf()
        )

        Spacer(modifier = Modifier.height(15.dp))

        AddFavoritesIconButton(iconColor = favoriteIconColor) {
            if (isMyFavoriteGame) deleteFromFavorites()
            else addToFavorites()
        }

        Spacer(modifier = Modifier.height(15.dp))

        DescriptionDetails(description = gameDetails?.descriptionRaw, headerIsVisible = true)

        Spacer(modifier = Modifier.height(20.dp))

        VideoGameDataSheetGrid(
            platforms = gameDetails?.platforms ?: arrayListOf(),
            genres = gameDetails?.genres ?: arrayListOf(),
            developers = gameDetails?.developers ?: arrayListOf(),
            publishers = gameDetails?.publishers ?: arrayListOf(),
            metaCritic = gameDetails?.metacritic ?: 0,
            released = gameDetails?.released ?: "",
            esrbRating = gameDetails?.esrbRatingResponse?.name ?: "N/A"
        )
    }
}