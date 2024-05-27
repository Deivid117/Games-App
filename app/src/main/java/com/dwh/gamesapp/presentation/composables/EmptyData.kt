package com.dwh.gamesapp.presentation.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyData(
    title: String,
    description: String,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = verticalArrangement
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
