package com.dwh.gamesapp.games_details.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.games_details.domain.model.Platforms
import com.dwh.gamesapp.games_details.presentation.utils.DateFormatter

@Composable
fun DateAndPlatforms(
    releaseDate: String,
    parentPlatforms: ArrayList<Platforms>
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GameDateText(releaseDate)

        Spacer(modifier = Modifier.width(10.dp))

        IconsGamePlatforms(parentPlatforms)
    }
}

@Composable
fun GameDateText(releaseDate: String) {
    Text(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(5.dp))
            .padding(5.dp),
        text = DateFormatter.formattedDate(releaseDate),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.background
    )
}

@Composable
fun IconsGamePlatforms(platforms: ArrayList<Platforms>) {
    val platformIcons = platforms.mapNotNull { platform ->
        when {
            platform.platform?.name?.contains("PC") == true -> R.drawable.ic_windows_logo
            platform.platform?.name?.contains("Xbox") == true -> R.drawable.ic_xbox_logo
            platform.platform?.name?.contains("PlayStation") == true -> R.drawable.ic_play_station_logo
            platform.platform?.name?.contains("Apple Macintosh") == true -> R.drawable.ic_mac_logo
            platform.platform?.name?.contains("Nintendo") == true -> R.drawable.ic_nintendo_logo
            else -> null
        }
    }
    val othersPlatforms = platforms.size - platformIcons.size

    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        platformIcons.forEach {
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = it),
                contentDescription = "platform icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
    if (othersPlatforms != 0)
        Text(
            text = "+$othersPlatforms",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
}