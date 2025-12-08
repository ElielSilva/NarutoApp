package com.example.narutoapp.repository.character

import com.example.narutoapp.data.entity.CharactersEntity
import com.example.narutoapp.models.Character


interface ICharacterRepository {

    // API
    suspend fun getAll(): ArrayList<Character>?
    suspend fun getById(id: Int): Character?

    // ROOM - Local database
    suspend fun save(character: CharactersEntity)
    suspend fun saveAll(characters: ArrayList<CharactersEntity>)

    suspend fun delete(id: Int)
    suspend fun deleteAll()
    suspend fun getFavorites(): ArrayList<CharactersEntity>?
    suspend fun getLocal(): ArrayList<CharactersEntity>
    suspend fun getLocalById(id: Int): CharactersEntity?

    suspend fun toggleFavorite(id: Int)
}