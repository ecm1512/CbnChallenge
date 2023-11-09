package com.example.mbcchallenge.data.service

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.mbcchallenge.data.database.LocalDataSource.Companion.ACCESS_TOKEN_KEY
import com.example.mbcchallenge.data.database.LocalDataSource.Companion.ACCESS_TOKEN_SAVED_TIME
import com.example.mbcchallenge.data.database.LocalDataSource.Companion.REFRESH_TOKEN_KEY
import com.example.mbcchallenge.data.database.dataStore
import com.example.mbcchallenge.data.service.request.LoginRequest
import com.example.mbcchallenge.data.service.request.RefreshTokenRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Content-Type", "application/json")

        val token = runBlocking {
            context.dataStore.data.map { prefs ->
                prefs[ACCESS_TOKEN_KEY]
            }.first()
        }
        val tokenTime = runBlocking {
            context.dataStore.data.map { prefs ->
                prefs[ACCESS_TOKEN_SAVED_TIME]
            }.first()
        }

        if (!token.isNullOrBlank() && tokenTime != null && System.currentTimeMillis() >= tokenTime) {
            val tokenApi = ServiceGenerator.generate(NimbleService::class.java)
            runBlocking {
                val refreshToken = context.dataStore.data.map { prefs ->
                    prefs[REFRESH_TOKEN_KEY]
                }.first() ?: ""
                val refreshTokenDto =
                    tokenApi.refreshTokenAsync(RefreshTokenRequest(refreshToken = refreshToken))
                context.dataStore.edit { preferences ->
                    preferences[REFRESH_TOKEN_KEY] = refreshTokenDto.data.attributes.refreshToken
                    preferences[ACCESS_TOKEN_KEY] = refreshTokenDto.data.attributes.accessToken
                    preferences[ACCESS_TOKEN_SAVED_TIME] =
                        System.currentTimeMillis() + 10 * 60 * 1000 //10 min in millis
                }
                request.addHeader("Authorization", "Bearer ${refreshTokenDto.data.attributes.refreshToken}")
            }
        } else {
            token?.let {
                request.addHeader("Authorization", "Bearer $token")
            }
        }

        return chain.proceed(request.build())
    }
}