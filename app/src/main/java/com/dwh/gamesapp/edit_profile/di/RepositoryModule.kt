package com.dwh.gamesapp.edit_profile.di

import com.dwh.gamesapp.edit_profile.data.repository.EditProfileRepositoryImpl
import com.dwh.gamesapp.edit_profile.domain.repository.EditProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideEditProfileRepository(editProfileRepositoryImpl: EditProfileRepositoryImpl): EditProfileRepository
}