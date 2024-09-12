package com.dwh.gamesapp.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameTextField

@Composable
fun ProfileUserInfo(
    name: String,
    email: String
) {
    GameTextField(
        modifier = Modifier.fillMaxWidth(),
        value = name,
        onValueChange = { },
        label = stringResource(id = R.string.signup_name_textfield_label),
        enabled = false,
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_person),
                contentDescription = "person icon",
                tint = Color.White
            )
        }
    )

    GameTextField(
        modifier = Modifier.fillMaxHeight(),
        value = email,
        onValueChange = { },
        label = stringResource(id = R.string.signup_email_textfield_label),
        enabled = false,
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_email),
                contentDescription = "email icon",
                tint = Color.White
            )
        }
    )
}
