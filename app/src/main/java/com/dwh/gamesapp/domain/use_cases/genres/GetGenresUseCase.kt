package com.dwh.gamesapp.domain.use_cases.genres

import com.dwh.gamesapp.domain.model.genres.GenresResults
import com.dwh.gamesapp.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {

    suspend operator fun invoke(): Flow<List<GenresResults>> {
        return genresRepository.getAllGenres()
    }
}