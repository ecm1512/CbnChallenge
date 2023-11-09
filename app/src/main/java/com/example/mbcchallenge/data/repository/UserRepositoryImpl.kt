package com.example.mbcchallenge.data.repository

import com.example.mbcchallenge.data.database.LocalDataSource
import com.example.mbcchallenge.data.utils.UserToken
import com.example.mbcchallenge.domain.repositories.UserRepository
import com.example.mbcchallenge.domain.utils.asExternalModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : UserRepository {
    override suspend fun getTokens(): UserToken {
        return localDataSource.getSavedTokens().asExternalModel()
    }

    override suspend fun clearTokens() {
        localDataSource.clearTokens()
    }
}