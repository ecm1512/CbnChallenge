package com.example.mbcchallenge.domain.utils

import com.example.mbcchallenge.data.database.entities.TokenEntity
import com.example.mbcchallenge.data.service.dto.BaseResponse
import com.example.mbcchallenge.data.utils.LoginData
import com.example.mbcchallenge.data.utils.UserToken

fun BaseResponse.asEntity() =
    TokenEntity(
        refreshToken = data.attributes.refreshToken,
        accessToken = data.attributes.accessToken,
        tokenTime = data.attributes.expiresIn.toLong()
    )

fun TokenEntity.asExternalModel2() =
    LoginData(
        refreshToken = refreshToken ?: "",
        accessToken = accessToken ?: ""
    )

fun TokenEntity.asExternalModel() =
    UserToken(
        refreshToken = refreshToken ?: "",
        accessToken = accessToken ?: ""
    )