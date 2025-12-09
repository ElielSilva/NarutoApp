package com.example.narutoapp.models

data class Village(
    val id: Int,
    val name: String,
    val region: String,
    val leader: String,
    val population: Int,
    val clans: List<String>
)