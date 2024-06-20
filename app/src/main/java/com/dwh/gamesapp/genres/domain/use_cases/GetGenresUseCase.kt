package com.dwh.gamesapp.genres.domain.use_cases

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.genres.domain.model.GenresResults
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    suspend operator fun invoke(): Flow<Resource<GenresResults>> {
        return genresRepository.getGenresFromApi()
    }
}