package com.example.narutoapp.data.entity

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "characters")
class CharactersEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val images: List<String>,
    val family: Map<String, String>,
    val jutsu: List<String>,
    val clan: List<String>,
    var isFavorite: Boolean = false,
)