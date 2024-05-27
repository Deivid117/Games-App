package com.dwh.gamesapp.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.presentation.composables.CustomButton
import com.dwh.gamesapp.presentation.composables.CustomTextField
import com.dwh.gamesapp.presentation.view_model.login.LoginViewModel
import com.dwh.gamesapp.utils.WavyShape
import com.dwh.gamesapp.presentation.navigation.Screens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        LoginContent(navController, viewModel)
    }
}

@Composable
fun LoginContent(
    navController: NavController,
    viewModel: LoginViewModel
) {
    Column(
        Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LoginIconApp()
        LoginCardContent(navController, viewModel)
    }

}

@Composable
fun LoginIconApp() {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.controller_image),
            contentDescription = "controller image"
        )
    }
}

@Composable
private fun LoginCardContent(
    navController: NavController,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(//150 10
            shape = WavyShape(period = 150.dp, amplitude = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
        ) {
            Column(Modifier.padding(
                horizontal = 15.dp,
                vertical = 40.dp
            )) {
                LoginForm(
                    email,
                    password,
                    onEmailChanged = { email = it },
                    onPasswordChanged = { password = it }
                )

                Spacer(modifier = Modifier.height(40.dp))

                LoginButtons( onClickLogin = {
                    viewModel.userLogin(email, password) {success ->
                        if(success) navController.navigate(Screens.HOME_SCREEN)
                    } }
                ) { navController.navigate(Screens.REGISTRATION_SCREEN) }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Todos los derechos reservados 2023",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}

@Composable
private fun LoginButtons(
    onClickLogin: () -> Unit,
    onClickRegistration: () -> Unit,

) {
    CustomButton(
        Modifier.fillMaxWidth(),
        onClick = { onClickLogin() },
        nameButton = stringResource(R.string.login_btn_login),
    )

    Spacer(modifier = Modifier.height(10.dp))

    CustomButton(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        onClick = { onClickRegistration() },
        nameButton = stringResource(R.string.login_btn_sign_up),
        isSecondaryButton = true
    )
}

@Composable
private fun LoginForm(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {

    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = { onEmailChanged(it) },
        placeholder = "Ingresa tu email",
        label = "Email",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    Spacer(modifier = Modifier.height(10.dp))

    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        placeholder = "Ingresa tu contraseña",
        label = "Password",
        isPasswordTextField = true
    )
}
