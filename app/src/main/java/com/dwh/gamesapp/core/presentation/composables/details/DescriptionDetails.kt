package com.dwh.gamesapp.core.presentation.composables.details

import android.text.Html
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DescriptionDetails(
    description: String?,
    headerIsVisible: Boolean = false
) {
    val formattedDescription = Html.fromHtml(description ?: "", Html.FROM_HTML_MODE_LEGACY).toString()

    if (headerIsVisible) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = formattedDescription,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}