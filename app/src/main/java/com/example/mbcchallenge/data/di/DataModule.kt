package com.example.mbcchallenge.data.di

import android.content.Context
import com.example.mbcchallenge.data.database.LocalDataSource
import com.example.mbcchallenge.data.repository.AuthRepositoryImpl
import com.example.mbcchallenge.data.repository.UserRepositoryImpl
import com.example.mbcchallenge.data.service.NimbleService
import com.example.mbcchallenge.data.service.NimbleServiceDataSource
import com.example.mbcchallenge.domain.repositories.AuthRepository
import com.example.mbcchallenge.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideNimbleServiceDataSource(
        service: NimbleService
    ) = NimbleServiceDataSource(service)

    @Singleton
    @Provides
    fun provideLocalDataSource(
        @ApplicationContext appContext: Context,
    ) = LocalDataSource(appContext)

    @Singleton
    @Provides
    fun provideAuthRepository(
        localDataSource: LocalDataSource,
        authRemoteDataSource: NimbleServiceDataSource
    ): AuthRepository = AuthRepositoryImpl(localDataSource, authRemoteDataSource)

    @Singleton
    @Provides
    fun provideUserRepository(
        localDataSource: LocalDataSource
    ): UserRepository = UserRepositoryImpl(localDataSource)
}