package com.example.narutoapp.repository.character

import com.example.narutoapp.models.AllCharacter
import com.example.narutoapp.models.AllCharacterItem

interface ICharacterRepository {
    suspend fun getAll(): AllCharacter?
    suspend fun getById(id: Int): AllCharacterItem?
}