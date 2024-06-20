package com.dwh.gamesapp.platforms.di

import com.dwh.gamesapp.platforms.data.repository.PlatformsRepositoryImpl
import com.dwh.gamesapp.platforms.domain.repository.PlatformsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providePlatformsRepository(platformsRepositoryImpl: PlatformsRepositoryImpl): PlatformsRepository
}