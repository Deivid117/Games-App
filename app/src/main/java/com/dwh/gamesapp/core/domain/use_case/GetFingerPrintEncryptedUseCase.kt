package com.dwh.gamesapp.core.domain.use_case

import android.content.Context
import com.dwh.gamesapp.core.domain.repository.CryptographyManagerRepository
import javax.inject.Inject

class GetFingerPrintEncryptedUseCase @Inject constructor(
    private val cryptographyManagerRepository: CryptographyManagerRepository
) {
    operator fun invoke(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ) = cryptographyManagerRepository.getFingerPrint(
        context = context,
        filename = filename,
        mode = mode,
        prefKey = prefKey
    )
}