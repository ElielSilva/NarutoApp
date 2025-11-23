package com.example.narutoapp.services

import com.example.narutoapp.models.AllCharacter
import com.example.narutoapp.models.AllCharacterItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface INarutoApi {
    @GET("/characters")
    suspend fun getAllCharacter() : Response<AllCharacter>

    @GET("/characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int) : Response<AllCharacterItem>
}