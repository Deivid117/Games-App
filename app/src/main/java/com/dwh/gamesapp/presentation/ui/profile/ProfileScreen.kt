package com.dwh.gamesapp.presentation.ui.profile

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.model.user.UserDataStore
import com.dwh.gamesapp.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.presentation.composables.CustomButton
import com.dwh.gamesapp.presentation.composables.CustomDialog
import com.dwh.gamesapp.presentation.composables.CustomScaffold
import com.dwh.gamesapp.presentation.view_model.logout.LogoutViewModel
import com.dwh.gamesapp.presentation.view_model.profile.ProfileUiState
import com.dwh.gamesapp.presentation.view_model.profile.ProfileViewModel
import mx.com.satoritech.creditaco.navigation.Screens

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: LogoutViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(profileViewModel) {
        profileViewModel.getUserInfo()
    }

    CustomScaffold(navController = navController) {
        BackgroundGradient()
        ValidationResponse(profileViewModel, viewModel, navController)
    }

}

@Composable
fun ValidationResponse(
    profileViewModel: ProfileViewModel,
    viewModel: LogoutViewModel,
    navController: NavController
) {
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is ProfileUiState.Success -> {
            val data = (uiState as ProfileUiState.Success).data
            profileViewModel.setUserDataStore(UserDataStore(
                data.id.toInt(),
                data.name,
                data.email,
                data.password,
                isLogged = data.isLogged ?: false
            ))

            CurvedBox(navController, viewModel, data, profileViewModel)
            UserImage()
        }
        is ProfileUiState.Error -> {
            val errorMessage = (uiState as ProfileUiState.Error).errorMessage
            Log.e("UIStateError", errorMessage)
        }
    }
}

@Composable
fun UserImage() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(180.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.fillMaxSize()
                .background(Color.White, CircleShape)
                .clip(
                    CircleShape
                )
                .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}

@Composable
private fun CurvedBox(
    navController: NavController,
    viewModel: LogoutViewModel,
    user: User,
    profileViewModel: ProfileViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
            .drawWithContent {
                val width = size.width
                val height = size.height
                val radius = 120f
                with(drawContext.canvas.nativeCanvas) {
                    val checkPoint = saveLayer(null, null)
                    drawContent()
                    drawArc(
                        color = Color.Transparent,
                        startAngle = 0f, // Ángulo de inicio para la parte superior
                        sweepAngle = 180f, // Ángulo de barrido de 180 grados para la parte superior
                        topLeft = Offset(0f, -radius), // Esquina superior izquierda
                        size = Size(
                            width,
                            2 * radius
                        ),// Ajusta el tamaño para cubrir toda la parte superior
                        useCenter = false,
                        blendMode = BlendMode.SrcOut
                    )
                    restoreToCount(checkPoint)
                }
            }
            .background(MaterialTheme.colorScheme.onSecondary)
    ) { ProfileContent(navController, viewModel, user, profileViewModel) }
}

@Composable
fun ProfileContent(
    navController: NavController,
    viewModel: LogoutViewModel,
    user: User,
    profileViewModel: ProfileViewModel
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    ShowLogoutDialog(
        showLogoutDialog,
        onCancel = { showLogoutDialog = false },
        onAccept = {
            showLogoutDialog = false
            viewModel.userLogout(user.id) {success ->
                if(success) navController.navigate(Screens.LOGIN_SCREEN)
            }
        }
    )

    Column(
        Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 40.dp, top = 140.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        UserDataItem("User name", user.name)

        HorizontalDivider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onSecondaryContainer)

        UserDataItem("Email", user.email)

        HorizontalDivider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onSecondaryContainer)
    }

    BottomProfileButtons(
        onClickLogout = { showLogoutDialog = true },
        onClickEdit = {
            navController.navigate(Screens.EDIT_PROFILE_SCREEN)
        }
    )
}

@Composable
private fun UserDataItem(title: String, body: String) {
    Text(text = title, style = MaterialTheme.typography.bodyMedium)
    Text(text = body, style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun BottomProfileButtons(
    onClickLogout: () -> Unit,
    onClickEdit: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        CustomButton(
            Modifier.fillMaxWidth(),
            onClick = { onClickEdit() },
            nameButton = "EDIT PROFILE"
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomButton(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = { onClickLogout() },
            nameButton = "Logout",
            isSecondaryButton = true
        )
    }
}

@Composable
fun ShowLogoutDialog(
    showLogoutDialog: Boolean,
    onCancel: () -> Unit,
    onAccept: () -> Unit
) {
    if(showLogoutDialog) {
        CustomDialog(
            animation = R.raw.broken_heart_animation,
            onDissmiss = { onCancel() },
            title = "¿Seguro que quieres cerrar sesión?",
            isLoggingOut = true
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { onAccept() }, nameButton = "Yes")
                CustomButton(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = { onCancel() }, nameButton = "No")
            }
        }
    }
}

