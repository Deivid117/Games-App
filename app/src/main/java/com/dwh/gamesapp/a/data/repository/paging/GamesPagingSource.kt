package com.dwh.gamesapp.a.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dwh.gamesapp.a.domain.model.game.GamesResults
import com.dwh.gamesapp.a.domain.repository.GamesRepository
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

class GamesPagingSource(
    private val gamesRepository: GamesRepository
): PagingSource<Int, GamesResults>() {
    override fun getRefreshKey(state: PagingState<Int, GamesResults>): Int? {
        return state.anchorPosition?.let {
            val page = state.closestPageToPosition(it)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesResults> {
        val currentPage = params.key ?: 1
        return try {
            val gamesResults = gamesRepository.getAllGames(currentPage, 20)
            val data = gamesResults.single()

            LoadResult.Page(
                data =  data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if(data.isNotEmpty()) currentPage + 1 else null
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
        catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}