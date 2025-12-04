package com.example.narutoapp.models

data class AllCharacterItem(
//    val father: Father,
//    val id: Int,
//    val images: List<Image>,
//    val jutsus: List<Jutsu>,
//    val mother: Mother,
//    val name: String,
//    val power: Int,
//    val profile_image: String,
//    val rank: String,
//    val summary: String,
//    val village: Village
    val id: Int,
    val name: String,
    val images: List<String>,
    val family: Map<String, String>,
    val jutsu: List<String>,
    val clan: List<String>,
)