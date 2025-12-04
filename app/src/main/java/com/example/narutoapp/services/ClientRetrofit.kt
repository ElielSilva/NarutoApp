package com.example.narutoapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientRetrofit : IClient {
    private const val BASE_URL = "https://express-js-on-vercel-kappa-ashy-74.vercel.app/" //""https://dattebayo-api.onrender.com" //"https://naruto-br-api.site"

    override fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}