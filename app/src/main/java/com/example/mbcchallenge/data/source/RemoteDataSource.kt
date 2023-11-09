package com.example.mbcchallenge.data.source

import com.example.mbcchallenge.data.service.dto.BaseResponse

interface RemoteDataSource {
    suspend fun login(mail: String, password: String): BaseResponse
}