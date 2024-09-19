package com.dwh.gamesapp.core.di

import com.dwh.gamesapp.core.domain.repository.CryptographyManagerRepository
import com.dwh.gamesapp.core.data.repository.CryptographyManagerRepositoryImpl
import com.dwh.gamesapp.core.data.local.preferences.repository.DataStoreRepositoryImp
import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
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
    abstract fun bindsCryptographyRepository(cryptographyManagerRepositoryImpl: CryptographyManagerRepositoryImpl): CryptographyManagerRepository
}