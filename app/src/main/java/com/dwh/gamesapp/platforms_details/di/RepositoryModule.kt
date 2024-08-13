package com.dwh.gamesapp.platforms_details.di

import com.dwh.gamesapp.platforms_details.data.repository.PlatformDetailsRepositoryImpl
import com.dwh.gamesapp.platforms_details.domain.repository.PlatformDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providePlatformDetailsRepository(platformDetailsRepositoryImpl: PlatformDetailsRepositoryImpl): PlatformDetailsRepository
}