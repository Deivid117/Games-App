package com.dwh.gamesapp.di

import com.dwh.gamesapp.data.data_source.GamesDataSource
import com.dwh.gamesapp.data.data_source.GamesDataSourceImp
import com.dwh.gamesapp.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dwh.gamesapp.domain.repository.*

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