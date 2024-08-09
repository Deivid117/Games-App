package com.dwh.gamesapp.genres.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres.domain.model.Genre
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<Genre>>> {
        return genresRepository.getGenresFromApi()
    }
}