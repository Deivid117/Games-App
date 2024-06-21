package com.dwh.gamesapp.a.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.theme.Dogica

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    nameButton: String,
    isSecondaryButton: Boolean = false
) {
    val cornerRadiusDefault = 8.dp
    val textStyle = if(!isSecondaryButton) MaterialTheme.typography.titleMedium.copy(fontFamily = Dogica) else MaterialTheme.typography.titleSmall.copy(fontFamily = Dogica)
    val roundedCornerShape = if(!isSecondaryButton) {
        RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomStart = 15.dp, bottomEnd = 15.dp)
    } else {
        RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 50.dp, bottomEnd = 50.dp)
    }
    val gradientColors =
        if(isSecondaryButton) {
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.primary
            )
        } else {
            listOf(
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.tertiary
            )
        }

    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(colors = gradientColors),
                shape = roundedCornerShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = modifier,
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(cornerRadiusDefault),
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = nameButton,
                style = textStyle
            )
        }
    }
}
