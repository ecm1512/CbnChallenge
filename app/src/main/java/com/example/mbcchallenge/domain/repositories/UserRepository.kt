package com.example.mbcchallenge.domain.repositories

import com.example.mbcchallenge.data.utils.UserToken

interface UserRepository {
    suspend fun getTokens(): UserToken
    suspend fun clearTokens()
}