package com.dwh.gamesapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.utils.modifier.shimmerAnimation

@Composable
fun HomeShimmerItem() {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(190.dp)
                .width(150.dp)
                .background(MaterialTheme.colorScheme.surface)
                .shimmerAnimation()
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .height(20.dp)
                .width(120.dp)
                .background(MaterialTheme.colorScheme.surface)
                .shimmerAnimation()
        )
    }
}