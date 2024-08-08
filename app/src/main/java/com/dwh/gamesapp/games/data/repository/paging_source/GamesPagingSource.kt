package com.dwh.gamesapp.games.data.repository.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.games.data.data_source.GamesDataSource
import com.dwh.gamesapp.games.domain.model.Game
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

class GamesPagingSource(
    private val gamesRepository: GamesDataSource
) : PagingSource<Int, Game>() {

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        val position = params.key ?: 1
        return try {
            var data = listOf<Game>()

            gamesRepository.getGames(position, 20).collectLatest {
                data = when (it) {
                    is Resource.Error -> emptyList()
                    is Resource.Loading -> TODO()
                    is Resource.Success -> it.data!!
                }
            }

            val nextKey = if (data.isNotEmpty()) {
                position + 1
            } else {
                null
            }

            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}