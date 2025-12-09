package com.example.narutoapp.repository.village

import android.util.Log
import com.example.narutoapp.data.dao.CharactersDao
import com.example.narutoapp.data.entity.CharactersEntity
import com.example.narutoapp.data.entity.toEntity
import com.example.narutoapp.data.entity.toModel
import com.example.narutoapp.models.Character
import com.example.narutoapp.models.Village
import com.example.narutoapp.repository.character.ICharacterRepository
import com.example.narutoapp.services.INarutoApi
import retrofit2.Retrofit
import java.util.ArrayList

class VillageRepositoryImpl(
    private val client: Retrofit
): IVillageRepository {

    private val instance: INarutoApi = client.create(INarutoApi::class.java)

    override suspend fun getAll(): ArrayList<Village>? {
        Log.d("VillageRepository", "Buscando vilas da API...")

        return try {
            val result = instance.getAllVillages()

            if (result.isSuccessful) {
                val body = result.body().orEmpty()
                Log.d("VillageRepository", "Número de vilas retornadas: ${body.size}")
                body
            } else {
                Log.e("VillageRepository", "Falha na requisição: ${result.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("VillageRepository", "Erro ao buscar vilas da API", e)
            null
        } as ArrayList<Village>?
    }

//    override suspend fun getAll(): ArrayList<Village>? {
//        TODO("Not yet implemented")
//    }
}