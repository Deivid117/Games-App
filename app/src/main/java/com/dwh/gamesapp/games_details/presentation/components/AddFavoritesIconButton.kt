package com.dwh.gamesapp.games_details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.utils.modifier.clickableSingle

@Composable
fun AddFavoritesIconButton(
    iconColor: Color,
    onClickAction: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = {}) {
            Icon(
                modifier = Modifier.clickableSingle(delay = 4000L) { onClickAction() }
                    .size(40.dp),
                painter = painterResource(id = R.drawable.ic_add_favorite),
                contentDescription = "kirby add to favorites icon",
                tint = iconColor
            )
        }
    }
}