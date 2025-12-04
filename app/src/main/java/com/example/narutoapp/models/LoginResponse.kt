package com.example.narutoapp.models

data class LoginResponse(
    val token: String,
    val userId: String,
    val message: String
)
