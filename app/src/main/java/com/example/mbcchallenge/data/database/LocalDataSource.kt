package com.example.mbcchallenge.data.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mbcchallenge.data.database.entities.TokenEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LocalDataSource(private val context: Context) {
    companion object {

        val ACCESS_TOKEN_SAVED_TIME = longPreferencesKey("token_time")

        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    }

    suspend fun saveTokenData(
        refreshToken: String,
        accessToken: String,
        time: Long
    ) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
            preferences[ACCESS_TOKEN_KEY] = accessToken
            preferences[ACCESS_TOKEN_SAVED_TIME] = time
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = ""
            preferences[ACCESS_TOKEN_KEY] = ""
            preferences[ACCESS_TOKEN_SAVED_TIME] = 0
        }
    }

    suspend fun getSavedTokens(): TokenEntity {
        val prefs = context.dataStore.data.first()
        val refreshToken = prefs[REFRESH_TOKEN_KEY] ?: ""
        val accessToken = prefs[ACCESS_TOKEN_KEY] ?: ""
        val tokenTime = prefs[ACCESS_TOKEN_SAVED_TIME]
        return TokenEntity(
            refreshToken = refreshToken,
            accessToken = accessToken,
            tokenTime = tokenTime
        )
    }

    val tokenDataFlow: Flow<TokenEntity> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                // Handle IO exceptions
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val refreshToken = prefs[REFRESH_TOKEN_KEY] ?: ""
            val accessToken = prefs[ACCESS_TOKEN_KEY] ?: ""
            TokenEntity(
                refreshToken = refreshToken,
                accessToken = accessToken
            )
        }
}