package com.dwh.gamesapp.a.domain.use_cases.genres

import com.dwh.gamesapp.a.domain.model.genres.GenresResults
import com.dwh.gamesapp.a.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {

    suspend operator fun invoke(): Flow<List<GenresResults>> {
        return genresRepository.getAllGenres()
    }
}