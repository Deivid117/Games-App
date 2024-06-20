package com.dwh.gamesapp.a.domain.use_cases.genre_details

import com.dwh.gamesapp.a.domain.model.genre_details.GenreDetails
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenreDetailsUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {

    suspend operator fun invoke(id: Int): Flow<GenreDetails> {
        return genresRepository.getGenreDetails(id)
    }
}