package com.example.narutoapp.repository.village

import com.example.narutoapp.models.Village;

import java.util.ArrayList;

public interface IVillageRepository {
    suspend fun getAll(): ArrayList<Village>?
}