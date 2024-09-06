package com.dwh.gamesapp.signup.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.a.presentation.composables.Avatars
import com.dwh.gamesapp.core.presentation.theme.dark_green

@Composable
fun VerticalGridAvatars(
    modifier: Modifier = Modifier,
    avatars: List<Avatars>,
    selectedAvatarId: Long,
    onClickAvatar: (Int, Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.wrapContentHeight(),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = avatars,
            key = { avatar -> avatar.id }
        ) {avatar ->
            AvatarItem(avatar, selectedAvatarId, onClickAvatar)
        }
    }
}

@Composable
private fun AvatarItem(
    avatar: Avatars,
    selectedAvatarId: Long,
    onClickAvatar: (Int, Long) -> Unit,
) {
    val isSelectedAvatar = selectedAvatarId == avatar.id
    val width = if (isSelectedAvatar) 5.dp else 0.dp
    val color = if (isSelectedAvatar) dark_green else Color.Transparent

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.White, CircleShape)
            .border(
                width = width,
                color = color,
                shape = CircleShape
            )
            .clickable { onClickAvatar(avatar.image, avatar.id) }
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            painter = painterResource(id = avatar.image),
            contentDescription = "avatar icon",
            contentScale = ContentScale.Crop
        )
    }
}