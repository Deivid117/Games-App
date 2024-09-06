package com.dwh.gamesapp.signup.di

import com.dwh.gamesapp.signup.data.repository.SignupRepositoryImpl
import com.dwh.gamesapp.signup.domain.repository.SignupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSignupRepository(registrationRepositoryImpl: SignupRepositoryImpl): SignupRepository
}