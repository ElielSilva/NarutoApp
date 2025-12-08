package com.example.narutoapp.repository.character

import com.example.narutoapp.data.dao.CharactersDao
import com.example.narutoapp.data.entity.toModel
import com.example.narutoapp.services.IClient
import com.example.narutoapp.services.INarutoApi


//class CharacterRepositoryImpl(private val clientService: IClient) : ICharacterRepository  {
//    private val instance: INarutoApi = clientService.getInstance().create<INarutoApi>()
//
//    override suspend fun getAll(): AllCharacter? {
//        val result =  instance.getAllCharacter()
//        if (result.isSuccessful) {
//            return result.body()
//        }
//        return null
//    }
//
//    override suspend fun getById(id: Int): AllCharacterItem? {
//        val result =  instance.getCharacter(id)
//        if (result.isSuccessful) {
//            return result.body()
//        }
//        return null
//    }
//}

class CharacterRepositoryImpl(
    private val clientService: IClient,
    private val charactersDao: CharactersDao
) : ICharacterRepository  {

    private val instance: INarutoApi = clientService.getInstance().create()

    override suspend fun toggleFavorite(id: Int) {
        var character = charactersDao.getById(id) ?: return
        val updated = character.copy(isFavorite = !character.isFavorite)
        charactersDao.update(updated)
    }

    override suspend fun getAll(): ArrayList<Character>? {

        val localData = charactersDao.getAll()
//        var x: AllCharacter = AllCharacter()

        if (localData.isNotEmpty()) {
            return localData.map { it.toModel() } as ArrayList<Character>?
        }

        val result = instance.getAllCharacter()

        return if (result.isSuccessful) {
            val body = result.body()


            body?.let {
                val entities = it.map { item ->
                    CharactersEntity(
                        id = item.id,
                        name = item.name,
                        images = item.images,
                        family = item.family,
                        jutsu = item.jutsu,
                        clan = item.clan
                    )
                }
                charactersDao.insertAll(entities)
            }

            body
        } else {
            null
        }
    }

    override suspend fun getById(id: Int): Character? {
        val result = instance.getCharacter(id)

        return if (result.isSuccessful) {
            val body = result.body()


            body?.let {
                val entity = CharactersEntity(
                    id = it.id,
                    name = it.name,
                    images = it.images,
                    family = it.family,
                    jutsu = it.jutsu,
                    clan = it.clan
                )
                charactersDao.insert(entity)
            }

            body
        } else null
    }

//
//    suspend fun deleteCharacter(id: Int) {
//        charactersDao.deleteById(id)
//    }
//
//
//    suspend fun deleteAllCharacters() {
//        charactersDao.deleteAll()
//    }

//    override suspend fun save(character: CharactersEntity) {
//        charactersDao.insert(character)
//    }
//
//    override suspend fun saveAll(characters: ArrayList<CharactersEntity>) {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun saveAll(characters: List<CharactersEntity>) {
//        charactersDao.insertAll(characters)
//    }
//
//    override suspend fun delete(id: Int) {
//        charactersDao.deleteById(id)
//    }
//
//    override suspend fun deleteAll() {
//        charactersDao.deleteAll()
//    }

//    override suspend fun getLocal(): List<CharactersEntity> {
//        return charactersDao.getAll()
//    }

//    override suspend fun getLocalById(id: Int): CharactersEntity? {
//        return charactersDao.getById(id)
//    }
}