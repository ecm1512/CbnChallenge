package com.example.mbcchallenge.domain.repositories

import com.example.mbcchallenge.data.utils.LoginData

interface AuthRepository {
    suspend fun login(mail: String, password: String): LoginData
}