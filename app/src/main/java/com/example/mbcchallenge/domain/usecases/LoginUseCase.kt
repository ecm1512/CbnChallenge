package com.example.mbcchallenge.domain.usecases

import com.example.mbcchallenge.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return authRepository.login(email,password).accessToken.isNullOrEmpty().not()
    }
}