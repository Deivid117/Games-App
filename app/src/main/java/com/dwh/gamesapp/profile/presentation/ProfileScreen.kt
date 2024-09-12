@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.profile.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.composables.GameOutlinedButton
import com.dwh.gamesapp.core.presentation.composables.GameTitleGradientText
import com.dwh.gamesapp.profile.presentation.components.ProfileUserAvatar
import com.dwh.gamesapp.profile.presentation.components.ProfileUserInfo

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    state: ProfileState,
    navigateToEditProfile: () -> Unit
) {
    GameScaffold(
        navController = navController,
        isTopAppBarVisible = false,
        showBackgroundGradient = false
    ) {
        ProfileView(
            viewModel = viewModel,
            state = state,
            navigateToEditProfile = navigateToEditProfile
        )
    }
}

@Composable
private fun ProfileView(
    viewModel: ProfileViewModel,
    state: ProfileState,
    navigateToEditProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 30.dp)
    ) {
        ProfileUserAvatar(state.user.profileAvatarId) { viewModel.setUserProfileAvatar(it) }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {

                GameTitleGradientText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.profile_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                ProfileUserInfo(name = state.user.name, email = state.user.email)

                Spacer(modifier = Modifier.height(15.dp))

                GameFilledButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    nameButton = stringResource(id = R.string.profile_btn_edit_profile)
                ) { navigateToEditProfile() }

                GameOutlinedButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    nameButton = stringResource(id = R.string.profile_btn_logout),
                    buttonSound = R.raw.sonic_ring,
                ) { viewModel.handleLogoutDialog(true) }
            }
        }
    }
}