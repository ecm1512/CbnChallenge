package com.example.mbcchallenge.data.service.request

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class LoginRequest(
    @SerializedName("grant_type")
    val grantType: String = "password",
    val email: String,
    val password: String,
    @SerializedName("client_id")
    val clientId: String = "ly1nj6n11vionaie65emwzk575hnnmrk",
    @SerializedName("client_secret")
    val clientSecret: String= "hOzsTeFlT6ko0dme22uGbQal04SBPYc1"
)

data class RefreshTokenRequest(
    @SerializedName("grant_type")
    val grantType: String = "refresh_token",
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("client_id")
    val clientId: String = "ly1nj6n11vionaie65emwzk575hnnmrk",
    @SerializedName("client_secret")
    val clientSecret: String= "hOzsTeFlT6ko0dme22uGbQal04SBPYc1"
)