package com.example.mbcchallenge.data.repository

import com.example.mbcchallenge.data.database.LocalDataSource
import com.example.mbcchallenge.data.service.NimbleServiceDataSource
import com.example.mbcchallenge.data.utils.LoginData
import com.example.mbcchallenge.domain.repositories.AuthRepository
import com.example.mbcchallenge.domain.utils.asEntity
import com.example.mbcchallenge.domain.utils.asExternalModel2
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: NimbleServiceDataSource
) : AuthRepository {
    override suspend fun login(mail: String, password: String): LoginData {
        val entity = remoteDataSource.login(mail, password).asEntity()

        localDataSource.saveTokenData(
            refreshToken = entity.refreshToken ?: "",
            accessToken = entity.accessToken ?: "",
            time = entity.tokenTime ?: 0
        )

        return entity.asExternalModel2()
    }
}