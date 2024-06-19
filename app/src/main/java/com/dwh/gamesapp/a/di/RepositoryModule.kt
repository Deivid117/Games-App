package com.dwh.gamesapp.a.di

import com.dwh.gamesapp.a.data.data_source.GamesDataSource
import com.dwh.gamesapp.a.data.data_source.GamesDataSourceImp
import com.dwh.gamesapp.a.data.repository.DataStoreRepositoryImp
import com.dwh.gamesapp.a.data.repository.GamesRepositoryImp
import com.dwh.gamesapp.a.data.repository.GenresRepositoryImp
import com.dwh.gamesapp.a.data.repository.PlatformsRepositoryImp
import com.dwh.gamesapp.a.data.repository.UserRepositoryImp
import com.dwh.gamesapp.a.domain.repository.DataStoreRepository
import com.dwh.gamesapp.a.domain.repository.GamesRepository
import com.dwh.gamesapp.a.domain.repository.GenresRepository
import com.dwh.gamesapp.a.domain.repository.PlatformsRepository
import com.dwh.gamesapp.a.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsDataStoreRepository(dataStoreRepositoryImp: DataStoreRepositoryImp): DataStoreRepository

    @Binds
    abstract fun bindsGamesRepository(gamesRepositoryImp: GamesRepositoryImp): GamesRepository

    @Binds
    abstract fun bindsLocalUserRepository(localUserRepository: UserRepositoryImp): UserRepository

    @Binds
    abstract fun bindsGamesDataSource(gamesDataSourceImp: GamesDataSourceImp): GamesDataSource

    @Binds
    abstract fun bindsGenresRepository(genresRepositoryImp: GenresRepositoryImp): GenresRepository

    @Binds
    abstract fun bindsPlatformsRepository(platformsRepositoryImp: PlatformsRepositoryImp): PlatformsRepository
}