package com.dwh.gamesapp.profile.di

import com.dwh.gamesapp.profile.data.repository.ProfileRepositoryImpl
import com.dwh.gamesapp.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}