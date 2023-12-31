package com.dwh.gamesapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dwh.gamesapp.R

@Composable
fun BackgroundGradient() {
    val gradient = Brush.linearGradient(
        0.0f to MaterialTheme.colorScheme.primary,
        500.0f to MaterialTheme.colorScheme.secondary,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                gradient
            )
    ) {
        val darkTheme = isSystemInDarkTheme()
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = if(darkTheme) R.drawable.dark_background_image else R.drawable.light_background_image),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop
        )
    }
}