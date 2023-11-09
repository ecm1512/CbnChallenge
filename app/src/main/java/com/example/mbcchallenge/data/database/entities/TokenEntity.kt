package com.example.mbcchallenge.data.database.entities

data class TokenEntity(
    val refreshToken: String?,
    val accessToken: String?,
    val tokenTime: Long? = null
)
