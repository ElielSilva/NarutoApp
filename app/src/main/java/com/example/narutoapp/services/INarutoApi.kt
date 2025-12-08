package com.example.narutoapp.services

import com.example.narutoapp.models.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface INarutoApi {
    @GET("/characters")
    suspend fun getAllCharacter() : Response<ArrayList<Character>>

    @GET("/characters/{id}")
    suspend fun getCharacter(@Path("id") id: Int) : Response<Character>
}