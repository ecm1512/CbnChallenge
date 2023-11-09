package com.example.mbcchallenge.data.utils

data class UserToken(
    val isUserExists: Boolean = false,
    val accessToken: String,
    val refreshToken: String
)
