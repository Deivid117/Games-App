package com.dwh.gamesapp.games_details.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.theme.dark_green
import com.dwh.gamesapp.core.presentation.theme.light_green
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.games_details.domain.model.Developers
import com.dwh.gamesapp.games_details.domain.model.Genres
import com.dwh.gamesapp.games_details.domain.model.Platforms
import com.dwh.gamesapp.games_details.domain.model.Publishers
import com.dwh.gamesapp.games_details.presentation.utils.DateFormatter
import com.dwh.gamesapp.games_details.presentation.utils.generateStringFromList

@Composable
fun VideoGameDataSheetGrid(
    platforms: ArrayList<Platforms>,
    genres: ArrayList<Genres>,
    developers: ArrayList<Developers>,
    publishers: ArrayList<Publishers>,
    metaCritic: Int,
    released: String,
    esrbRating: String
) {
    val platformsString = generateStringFromList(platforms) { it.platform?.name ?: "N/A" }
    val genresStrings = generateStringFromList(genres) { it.name ?: "N/A" }
    val developersStrings = generateStringFromList(developers) { it.name ?: "N/A" }
    val publishersStrings = generateStringFromList(publishers) { it.name ?: "N/A" }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        /** Plataforms and Metascore */
        GameDataSheetTitlesRow(
            title1 = "Platforms",
            title2 = "Metascore",
        )
        GameDataSheetValuesRow(
            value1 = platformsString,
            value2 = "$metaCritic",
            showMetaCriticItem = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        /** Genre and Release date */
        GameDataSheetTitlesRow(
            title1 = "Genre",
            title2 = "Release date",
        )
        GameDataSheetValuesRow(
            value1 = genresStrings,
            value2 = DateFormatter.formattedDate(released)
        )

        Spacer(modifier = Modifier.height(20.dp))

        /** Developer and Publisher */
        GameDataSheetTitlesRow(
            title1 = "Developer",
            title2 = "Publisher",
        )
        GameDataSheetValuesRow(
            modifier = Modifier.fillMaxWidth(),
            value1 = developersStrings,
            value2 = publishersStrings
        )

        Spacer(modifier = Modifier.height(20.dp))

        /** Age rating */
        GameDataSheetTitlesRow(
            title1 = "Age rating",
            showRightItem = false
        )
        GameDataSheetValuesRow(
            value1 = esrbRating,
            showRightItem = false
        )
    }
}

@Composable
fun GameDataSheetTitlesRow(
    title1: String,
    title2: String = "",
    showRightItem: Boolean = true
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GameDataSheetText(text = title1)
        if (showRightItem) {
            GameDataSheetText(text = title2)
        }
    }
}

@Composable
fun GameDataSheetValuesRow(
    modifier: Modifier = Modifier,
    value1: String,
    value2: String = "",
    showRightItem: Boolean = true,
    showMetaCriticItem: Boolean = false
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GameDataSheetText(
            text = value1,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.inverseSurface
        )
        if (showRightItem) {
            if (showMetaCriticItem) {
                MetaCriticText(text = value2)
            } else {
                GameDataSheetText(
                    modifier = modifier,
                    text = value2,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
            }
        }
    }
}

@Composable
fun GameDataSheetText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        modifier = modifier.fillMaxWidth(0.45f),
        text = text,
        style = style,
        color = color
    )
}

@Composable
fun MetaCriticText(text: String) {
    val metaCriticColor = if (isDarkThemeEnabled()) dark_green else light_green

    Text(
        modifier = Modifier
            .border(1.dp, metaCriticColor, shape = RoundedCornerShape(5.dp))
            .padding(3.dp),
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = metaCriticColor
    )
}