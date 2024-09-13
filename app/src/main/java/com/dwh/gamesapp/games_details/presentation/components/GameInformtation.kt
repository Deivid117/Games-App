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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dwh.gamesapp.core.presentation.composables.details.DescriptionDetails
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.games_details.presentation.GameDetailsViewModel

@Composable
fun GameInformation(
    scrollState: ScrollState,
    gameDetails: GameDetails?,
    viewModel: GameDetailsViewModel,
) {
    val context = LocalContext.current
    val isMyFavoriteGame by viewModel.favoriteGame.collectAsStateWithLifecycle()
    var isSelected by remember { mutableStateOf(isMyFavoriteGame) }
    val iconTint = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(Constants.headerHeight - 50.dp))

        DateAndPlatforms(
            releaseDate = gameDetails?.released ?: "",
            parentPlatforms = gameDetails?.parentPlatforms ?: arrayListOf()
        )

        Spacer(modifier = Modifier.height(15.dp))

        /** TODO: Re hacer la lógica */
        AddFavoritesIconButton(viewModel, gameDetails, iconTint) {
            /*if(isSelected) {
                viewModel.removerFavoriteGame(gameDetails.id) {success ->
                    if(success) {
                        isSelected = !isSelected
                        Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                viewModel.addFavoriteGame(
                    FavoritGame(
                        id = gameDetails.id,
                        name = gameDetails.nameOriginal,
                        released = gameDetails.released,
                        background_image = gameDetails.backgroundImage,
                        metacritic = gameDetails.metacritic
                    )
                ) {
                    if (it) {
                        isSelected = !isSelected
                        Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()

                    }
                }
            }*/
        }

        Spacer(modifier = Modifier.height(15.dp))

        DescriptionDetails(gameDetails?.descriptionRaw, isHeaderVisible = true)

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