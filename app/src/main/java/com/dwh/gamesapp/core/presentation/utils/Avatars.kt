package com.dwh.gamesapp.core.presentation.utils

import com.dwh.gamesapp.R

sealed class Avatars(
    val id: Long,
    val image: Int
) {
    object Avatar1: Avatars(
        id = 1,
        image = R.drawable.avatar_icon_1
    )
    object Avatar2: Avatars(
        id = 2,
        image = R.drawable.avatar_icon_2
    )
    object Avatar3: Avatars(
        id = 3,
        image = R.drawable.avatar_icon_3
    )
}