package com.dwh.gamesapp.domain.use_cases.genre_details

import com.dwh.gamesapp.domain.model.genre_details.GenreDetails
import com.dwh.gamesapp.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreDetailsUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {

    suspend operator fun invoke(id: Int): Flow<GenreDetails> {
        return genresRepository.getGenreDetails(id)
    }
}