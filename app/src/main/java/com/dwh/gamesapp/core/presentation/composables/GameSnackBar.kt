package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameSnackBar(
    errorMessage: String,
    onRefreshGames: () -> Unit
) {
    Snackbar(
        modifier = Modifier.padding(15.dp),
        action = {
            TextButton(onClick = {
                onRefreshGames()
            }) {
                Text(
                    text = "Hola",
                    color = Color.Green,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    ) {
        Text(
            text = errorMessage
        )
    }
}