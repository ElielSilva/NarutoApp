package com.example.narutoapp.services

import retrofit2.Retrofit

interface IClient {
    fun getInstance(): Retrofit
}