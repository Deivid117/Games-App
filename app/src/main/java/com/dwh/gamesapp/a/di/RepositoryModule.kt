package com.dwh.gamesapp.a.di

import com.dwh.gamesapp.a.data.repository.DataStoreRepositoryImp
import com.dwh.gamesapp.a.data.repository.UserRepositoryImp
import com.dwh.gamesapp.a.domain.repository.DataStoreRepository
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
    abstract fun bindsLocalUserRepository(localUserRepository: UserRepositoryImp): UserRepository
}