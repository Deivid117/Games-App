package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.theme.Dogica
import com.dwh.gamesapp.core.presentation.theme.secondary
import com.dwh.gamesapp.core.presentation.utils.modifier.bounceClickEffect

@Composable
fun GameEnableBiometricDialog(
    onEnable: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Enable Biometric",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = Dogica)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Activa la autenticación biometrica para iniciar sesión con tu huella",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "* Solo puedes habilitar una vez tu huella para un único usuario",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        confirmButton = {
            TextButton(
                modifier = Modifier.bounceClickEffect(),
                onClick = { onEnable() },
                colors = ButtonDefaults.textButtonColors(contentColor = secondary)
            ) {
                Text(
                    text = "Enable",
                    style = MaterialTheme.typography.titleSmall.copy(fontFamily = Dogica, fontWeight = FontWeight.Bold)
                )
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier.bounceClickEffect(),
                onClick = { onDismiss() },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.titleSmall.copy(fontFamily = Dogica, fontWeight = FontWeight.Bold)
                )
            }
        }
    )
}