package com.dwh.gamesapp.genres_details.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.genres_details.domain.repository.GenreDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreDetailsUseCase @Inject constructor(
    private val genreDetailsRepository: GenreDetailsRepository
) {
    suspend operator fun invoke(id: Int): Flow<DataState<GenreDetails>> {
        return genreDetailsRepository.getGenreDetailsFromApi(id)
    }
}