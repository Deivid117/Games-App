package com.dwh.gamesapp.games_details.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.games_details.presentation.GameDetailsViewModel

@Composable
fun AddFavoritesIconButton(
    viewModel: GameDetailsViewModel,
    gameDetails: GameDetails?,
    colorTint: Color,
    onClickAction: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable {
                    onClickAction()
                },
            painter = painterResource(id = R.drawable.ic_add_favorite),
            contentDescription = "kirby add to favorites icon",
            tint = colorTint
        )
    }
}