package com.example.narutoapp.models

data class Character(
    val id: Int,
    val name: String?,
    val images: List<String>?,
    val family: Map<String, String>?,
    val jutsu: List<String>?,
    val clan: List<String>?,
    val isFavorite: Boolean = false,
)