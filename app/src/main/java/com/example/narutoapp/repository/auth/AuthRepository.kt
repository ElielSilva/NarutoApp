package com.example.narutoapp.repository.auth

import com.example.narutoapp.models.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoginResponse>
}