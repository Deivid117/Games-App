package com.dwh.gamesapp.presentation.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    isPasswordTextField: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = if(isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )
        },
        singleLine = true,
        label = {
            Text(
                text = label,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        ),
        trailingIcon = {
            if(isPasswordTextField) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    if (isPasswordVisible) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.ic_eye_active),
                            contentDescription = "",
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(id = R.drawable.ic_eye_inactive),
                            contentDescription = "",
                        )
                    }
                }
            }
        },
        visualTransformation = if(isPasswordTextField) if (isPasswordVisible) visualTransformation else PasswordVisualTransformation() else visualTransformation,
        keyboardOptions = keyboardOptions
    )

}