package com.example.narutoapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.narutoapp.data.entity.CharactersEntity
import com.example.narutoapp.models.Character

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharactersEntity>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharactersEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharactersEntity)

    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("UPDATE characters SET isFavorite = :value WHERE id = :id")
    suspend fun updateFavorite(id: Int, value: Boolean)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(update: Character)
}