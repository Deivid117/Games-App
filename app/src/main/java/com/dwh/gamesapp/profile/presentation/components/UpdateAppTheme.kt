package com.dwh.gamesapp.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.Dogica
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient

@Composable
fun UpdateAppTheme(
    onClickHandleModalBottomSheet: () -> Unit
) {
    val brushGradientColor = Brush.linearGradient(
        0.0f to secondary_gradient,
        500.0f to primary_gradient,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickHandleModalBottomSheet() }
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(brush = brushGradientColor, shape = CircleShape)
                .padding(3.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Brightness4,
                contentDescription = "email icon",
                tint = Color.White
            )
        }

        Text(
            text = stringResource(id = R.string.profile_update_theme),
            style = MaterialTheme.typography.bodySmall.copy(fontFamily = Dogica)
        )
    }
}