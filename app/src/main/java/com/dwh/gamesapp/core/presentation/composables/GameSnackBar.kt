package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameSnackBar(snackBarMessages: String) {
    Snackbar(
        modifier = Modifier.padding(15.dp)
    ) {
        Text(
            text = snackBarMessages
        )
    }
}