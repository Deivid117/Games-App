package com.dwh.gamesapp.login.presentation.components

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dwh.gamesapp.core.presentation.theme.secondary

@Composable
fun RememberMeCheckBox(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Checkbox(
        checked = isChecked,
        colors = CheckboxDefaults.colors(checkedColor = secondary, checkmarkColor = Color.White),
        onCheckedChange = {
            isChecked = it
            onCheckedChange(it)
        }
    )

    Text(
        modifier = modifier,
        text = "Remember me",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.outline
    )
}