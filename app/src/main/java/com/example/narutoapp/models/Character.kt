package com.example.narutoapp.models

data class Character(
    val id: Int,
    val name: String,
    val images: List<String>,
    val debut: Debut,
    val family: Map<String, String>,
    val jutsu: List<String>,
    val natureType: List<String>,
    val personal: Personal,
    val tools: List<String>,
)