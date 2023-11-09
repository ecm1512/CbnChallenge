package com.example.mbcchallenge.data.service

import com.example.mbcchallenge.data.service.dto.BaseResponse
import com.example.mbcchallenge.data.service.request.LoginRequest
import com.example.mbcchallenge.data.service.request.RefreshTokenRequest
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NimbleService {

    @POST("oauth/token")
    suspend fun login(
        @Body request: LoginRequest
    ): BaseResponse

    @POST("oauth/token")
    suspend fun refreshTokenAsync(
        @Body request: RefreshTokenRequest
    ): BaseResponse
}