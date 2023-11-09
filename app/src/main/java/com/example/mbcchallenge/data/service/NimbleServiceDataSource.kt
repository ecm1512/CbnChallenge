package com.example.mbcchallenge.data.service

import com.example.mbcchallenge.data.service.dto.BaseResponse
import com.example.mbcchallenge.data.service.request.LoginRequest
import com.example.mbcchallenge.data.source.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NimbleServiceDataSource @Inject constructor(private val service: NimbleService): RemoteDataSource {
    override suspend fun login(mail: String, password: String): BaseResponse {
        return withContext(Dispatchers.IO){
            service.login(LoginRequest(email = mail, password = password))
        }
    }
}