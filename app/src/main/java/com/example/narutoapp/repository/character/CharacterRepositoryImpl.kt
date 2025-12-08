package com.example.narutoapp.repository.character

import android.util.Log
import com.example.narutoapp.data.dao.CharactersDao
import com.example.narutoapp.data.entity.CharactersEntity
import com.example.narutoapp.data.entity.toEntity
import com.example.narutoapp.data.entity.toModel
import com.example.narutoapp.models.Character
import com.example.narutoapp.services.INarutoApi
import retrofit2.Retrofit

class CharacterRepositoryImpl(
    private val client: Retrofit,
    private val charactersDao: CharactersDao
) : ICharacterRepository  {

    private val instance: INarutoApi = client.create(INarutoApi::class.java)

    override suspend fun toggleFavorite(id: Int) {
        val character = charactersDao.getById(id)
        Log.d("CharacterRepository", "character = $character")
        character.isFavorite = !character.isFavorite
        Log.d("CharacterRepository", "character update = $character")
        val row = charactersDao.update( character)
        Log.d("CharacterRepository", "rows updated = $row")
        Log.d("CharacterRepository", "updated concluido")
    }

    override suspend fun getAll(): ArrayList<Character>? {

        Log.d("CharacterRepository", "Buscando personagens do ROM...")
        val localData = charactersDao.getAll()
        Log.d("CharacterRepository", localData.toString())
        if (localData.isNotEmpty()) {
            Log.d("CharacterRepository", "Retornando personagens do ROM...")
            return localData.map { it.toModel() } as ArrayList<Character>?
        }

        val result = instance.getAllCharacter()
        Log.d("CharacterRepository", "Buscando personagens da API...")
        Log.d(
            "CharacterRepository",
            "Numeros de objetos do returno" + result.body()?.size.toString()
        )
        return if (result.isSuccessful) {
            val body = result.body().orEmpty()

            try {
                val entities = body.map { it.toEntity() }
                entities.forEach {
                    Log.d("CharacterRepository", "id = ${it.id}")
                }
                charactersDao.insertAll(entities)

                val afterInsert = charactersDao.getAll()
                Log.d("CharacterRepository", "Depois do insert, count = ${afterInsert.size}")
            } catch (
                e: Exception
            ) {
                Log.e("CharacterRepository", "Erro ao inserir personagens no banco local", e)
            }

            ArrayList(body)
        } else {
            null
        }
    }

    override suspend fun getById(id: Int): Character? {
        Log.d("CharacterRepository", "Buscando personagem do ROM by id... ${id}")
        val localData = charactersDao.getById(id)
        Log.d("CharacterRepository", localData.toString())
        return localData.toModel()
    }

    override suspend fun save(character: CharactersEntity) {
        charactersDao.insert(character)
    }

    override suspend fun saveAll(characters: ArrayList<CharactersEntity>) {
        charactersDao.insertAll(characters)
    }

    override suspend fun delete(id: Int) {
        charactersDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        charactersDao.deleteAll()
    }

    override suspend fun getLocal(): ArrayList<CharactersEntity> {
        return charactersDao.getAll() as ArrayList<CharactersEntity>
    }

    override suspend fun getLocalById(id: Int): CharactersEntity? {
        return charactersDao.getById(id)
    }
}