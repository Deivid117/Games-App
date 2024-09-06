package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient

@Composable
fun GameUserImage(
    image: Int = R.drawable.user,
    onClick: () -> Unit = {}
) {
    val brushGradientColor = Brush.linearGradient(
        0.0f to secondary_gradient,
        500.0f to primary_gradient,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(180.dp)
                .background(brush = brushGradientColor, shape = CircleShape)
                .clip(CircleShape)
                .clickable { onClick() }
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White, shape = CircleShape)
                    .clip(CircleShape)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    painter = painterResource(id = image),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}