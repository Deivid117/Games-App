package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.utils.modifier.shimmerAnimation

@Composable
fun PlatformAndGenreShimmerItem() {
    Card(
        modifier = Modifier.size(200.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) { Box(modifier = Modifier.fillMaxSize().shimmerAnimation()) }
}