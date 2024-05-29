package com.dwh.gamesapp.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dwh.gamesapp.presentation.ui.theme.Dark_Green

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

                AvatarsLazyVerticalGrid(avatars, selectedAvatarId) { image, id ->
                    onSelectedAvatar(image, id)
                    selectedAvatarId = id
                }

                Spacer(modifier = Modifier.height(20.dp))

                CustomButton(onClick = { onDismiss() }, nameButton = "Aceptar" )
            }
        }
    }
}

@Composable
private fun AvatarsLazyVerticalGrid(
    avatars: List<Avatars>,
    selectedAvatarId: Long,
    onSelectedAvatar: (Int, Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxHeight(0.7f),
        columns = GridCells.Adaptive(90.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(avatars) { avatar ->
            AvatarItem(avatar, selectedAvatarId, onSelectedAvatar)
        }
    }
}

@Composable
private fun AvatarItem(
    avatar: Avatars,
    selectedAvatarId: Long,
    onSelectedAvatar: (Int, Long) -> Unit
) {
    val isSelectedAvatar = selectedAvatarId == avatar.id

    Box(modifier = Modifier
        .size(90.dp)
        .border(
            width = if (isSelectedAvatar) 5.dp else 0.dp,
            color = if (isSelectedAvatar) Dark_Green else Color.Transparent,
            shape = CircleShape
        )
        .background(Color.White, CircleShape)
        .padding(5.dp)
        .clickable {
            onSelectedAvatar(avatar.image, avatar.id)
        },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            painter = painterResource(id = avatar.image),
            contentDescription = "icon avatar",
            contentScale = ContentScale.Crop
        )
    }
}