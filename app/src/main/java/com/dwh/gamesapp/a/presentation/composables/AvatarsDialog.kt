package com.dwh.gamesapp.a.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AvatarsDialog(
    onDismiss: () -> Unit,
    onSelectedAvatar: (Int, Long) -> Unit
) {

    val avatars = listOf(
        Avatars.Avatar1,
        Avatars.Avatar2,
        Avatars.Avatar3,
    )
    var selectedAvatarId by remember { mutableLongStateOf(0L) }

    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                /*AvatarsLazyVerticalGrid(avatars, selectedAvatarId) { image, id ->
                    onSelectedAvatar(image, id)
                    selectedAvatarId = id
                }*/

                Spacer(modifier = Modifier.height(20.dp))

                CustomButton(onClick = { onDismiss() }, nameButton = "Aceptar" )
            }
        }
    }
}
