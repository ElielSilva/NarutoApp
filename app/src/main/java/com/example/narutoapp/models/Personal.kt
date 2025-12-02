package com.example.narutoapp.models

data class Personal(
    val birthdate: String,
    val sex: String,
    val bloodType: String,
    val clan: List<String>,
)