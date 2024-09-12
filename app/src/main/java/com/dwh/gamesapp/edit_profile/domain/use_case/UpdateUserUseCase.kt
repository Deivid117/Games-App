package com.dwh.gamesapp.edit_profile.domain.use_case

import com.dwh.gamesapp.edit_profile.domain.repository.EditProfileRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val editProfileRepository: EditProfileRepository,
) {
    suspend operator fun invoke(name: String, password: String, profileAvatarId: Long, id: Long) =
        editProfileRepository.updateUser(name, password, profileAvatarId, id)
}