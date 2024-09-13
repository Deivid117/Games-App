package com.dwh.gamesapp.platforms_details.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.composables.details.DescriptionDetails
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails

@Composable
fun PlatformGameInformation(
    scrollState: ScrollState,
    platformDetails: PlatformDetails?,
    platformGames: List<PlatformGame>
) {
    val description = if (platformDetails?.description.isNullOrEmpty()) "N/A" else platformDetails?.description

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(Constants.headerHeight - 50.dp))

        DescriptionDetails(description)

        Spacer(modifier = Modifier.height(15.dp))

        PopularGamesPlatform(platformGames)
    }
}