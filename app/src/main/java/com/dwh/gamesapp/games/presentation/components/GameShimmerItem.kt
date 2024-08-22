package com.dwh.gamesapp.games.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.utils.modifier.shimmerAnimation

@Composable
fun GameShimmerItem(heightRandom: Int) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(.8f))) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(heightRandom.dp)
                    .shimmerAnimation()
            )

            Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .shimmerAnimation()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .shimmerAnimation()
                )
            }
        }
    }
}