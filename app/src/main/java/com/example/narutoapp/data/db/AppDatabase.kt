package com.example.narutoapp.data.AppDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.example.narutoapp.data.dao.CharactersDao
import com.example.narutoapp.data.entity.CharactersEntity

@Database(entities = [CharactersEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: android.content.Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app.db"
                ).build().also { INSTANCE = it }
            }
    }
}