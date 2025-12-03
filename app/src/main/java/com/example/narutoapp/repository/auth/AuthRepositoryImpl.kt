package com.example.narutoapp.repository.auth

import com.example.narutoapp.models.LoginResponse

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            var messageError: String? = null
            if (email.isEmpty() || password.isEmpty()) {
                messageError = "Email and password must not be empty."
            } else if (!email.contains("@")) {
                messageError = "Invalid email format."
            } else if (password.length < 6) {
                messageError = "Password must be at least 6 characters long."
            }
            if (messageError != null) {
                throw IllegalArgumentException(messageError)
            }
            val response = LoginResponse("dummy_token", "dummy_user_id", "Login successful")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}