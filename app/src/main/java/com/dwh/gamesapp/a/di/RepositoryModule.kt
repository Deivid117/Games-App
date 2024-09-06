package com.dwh.gamesapp.a.di

import com.dwh.gamesapp.core.data.local.preferences.repository.DataStoreRepositoryImp
import com.dwh.gamesapp.login.data.repository.LoginRepositoryImpl
import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import com.dwh.gamesapp.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsDataStoreRepository(dataStoreRepositoryImp: DataStoreRepositoryImp): DataStoreRepository

}