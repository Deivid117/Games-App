package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled

@Composable
fun GameSnackBar(
    snackBarMessages: String,
    showSnackBarDismissAction: Boolean,
    lottieAnimation: Int,
    borderColorSnackBar: Color,
    containerColor: Color,
    lottieBackgroundColor: Color,
    onDismiss: () -> Unit = {}
) {
    val borderColor = if (!isDarkThemeEnabled()) borderColorSnackBar else Color.Transparent

    Snackbar(
        modifier = Modifier
            .padding(10.dp)
            .border(2.dp, color = borderColor, shape = RoundedCornerShape(10.dp)),
        containerColor = containerColor,
        shape = RoundedCornerShape(10.dp),
        dismissAction = {
            if (showSnackBarDismissAction) {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = "Ok", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = lottieBackgroundColor, shape = CircleShape)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                GameLoadingAnimation(lottieComposition = lottieAnimation, size = 40)
            }

            Text(
                text = snackBarMessages,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Justify
            )
        }
    }
}