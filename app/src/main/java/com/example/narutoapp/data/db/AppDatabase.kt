package com.example.narutoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.narutoapp.data.dao.CharactersDao
import com.example.narutoapp.data.entity.CharactersEntity
import com.example.narutoapp.utils.Converts

@Database(entities = [CharactersEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converts::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app.db"
                ).build().also { INSTANCE = it }
            }
    }
}