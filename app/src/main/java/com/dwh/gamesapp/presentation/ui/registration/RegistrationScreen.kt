package com.dwh.gamesapp.presentation.ui.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.presentation.composables.CustomButton
import com.dwh.gamesapp.presentation.composables.CustomDialog
import com.dwh.gamesapp.presentation.composables.CustomTextField
import com.dwh.gamesapp.presentation.ui.theme.Dogica
import com.dwh.gamesapp.presentation.view_model.registration.RegistrationViewModel

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        RegistrationContent(viewModel, navController)
    }
}

@Composable
fun RegistrationContent(viewModel: RegistrationViewModel, navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }

    ShowSuccessDialog(showSuccessDialog) {
        showSuccessDialog = false
        navController.popBackStack()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(
                horizontal = 15.dp,
                vertical = 40.dp
            )
    ) {
        Card(
            Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary))
        {
            Column(
                Modifier
                    .padding(15.dp)
                    .padding(bottom = 30.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "REGISTRO",
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = Dogica),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(30.dp))

                RegistrationForm(
                    name,
                    email,
                    password,
                    passwordConfirmation,
                    onNameChanged = { name = it },
                    onEmailChanged = { email = it },
                    onPasswordChanged = { password = it },
                    onPasswordConfirmationChanged = { passwordConfirmation = it }
                )
            }

            BottomRegistrationButton {
                viewModel.registerUser(
                    User(
                    name = name,
                    email = email,
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    isLogged = false
                )
                ) { succes ->
                    if(succes) {
                        name = ""
                        email = ""
                        password = ""
                        passwordConfirmation = ""
                        showSuccessDialog = true
                    }
                }
            }
        }
    }
}

@Composable
private fun RegistrationForm(
    name: String,
    email: String,
    password: String,
    passwordConfirmation: String,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmationChanged: (String) -> Unit
) {
    CustomTextField(
        Modifier.fillMaxWidth(),
        value = name,
        onValueChange = { onNameChanged(it) },
        placeholder = "Ingresa tu nombre",
        label = "Nombre de usuario"
    )

    Spacer(modifier = Modifier.height(10.dp))

    CustomTextField(
        Modifier.fillMaxWidth(),
        value = email,
        onValueChange = { onEmailChanged(it) },
        placeholder = "Ingresa tu email",
        label = "Email",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    Spacer(modifier = Modifier.height(10.dp))

    CustomTextField(
        Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        placeholder = "Ingresa tu contraseña",
        label = "Password",
        isPasswordTextField = true
    )

    Spacer(modifier = Modifier.height(10.dp))

    CustomTextField(
        Modifier.fillMaxWidth(),
        value = passwordConfirmation,
        onValueChange = { onPasswordConfirmationChanged(it) },
        placeholder = "Confirma tu contraseña",
        label = "Password Confirmation",
        isPasswordTextField = true
    )
}

@Composable
private fun BottomRegistrationButton(
    onClickAction: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        CustomButton(
            Modifier.fillMaxWidth(),
            onClick = { onClickAction() },
            nameButton = "CREAR CUENTA"
        )
    }
}

@Composable
private fun ShowSuccessDialog(
    showSuccessDialog: Boolean,
    onDissmiss: () -> Unit
) {
    if(showSuccessDialog) {
        CustomDialog(
            animation = R.raw.heart_animation,
            onDissmiss = { onDissmiss() },
            title = "Te haz registrado con éxito",
        ) {}
    }
}
