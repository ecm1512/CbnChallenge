package com.example.mbcchallenge.domain.utils

sealed class Response <out R> {
    data class Success<out T>(val data: T): Response<T>()
    data class Error(val message: String): Response<Nothing>()
    object Loading : Response<Nothing>()
    object Finished: Response<Nothing>()
}
