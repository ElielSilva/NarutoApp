package com.example.narutoapp.repository.character

import com.example.narutoapp.models.AllCharacter
import com.example.narutoapp.models.AllCharacterItem
import com.example.narutoapp.services.IClient
import com.example.narutoapp.services.INarutoApi
import retrofit2.create

class CharacterRepository(private val clientService: IClient) : ICharacterRepository  {
    private val instance: INarutoApi = clientService.getInstance().create<INarutoApi>()

    override suspend fun getAll(): AllCharacter? {
        val result =  instance.getAllCharacter()
        if (result.isSuccessful) {
            return result.body()
        }
        return null
    }

    override suspend fun getById(id: Int): AllCharacterItem? {
        val result =  instance.getCharacter(id)
        if (result.isSuccessful) {
            return result.body()
        }
        return null
    }
}