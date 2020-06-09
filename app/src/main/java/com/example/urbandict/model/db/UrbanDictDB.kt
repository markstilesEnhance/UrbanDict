package com.example.urbandict.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.urbandict.model.DefinitionItem

@Database(entities = [DefinitionItem::class], version = 1, exportSchema = false)
abstract class UrbanDictDB: RoomDatabase() {

    abstract fun defsDao(): UrbanDictDAO

    companion object{

        @Volatile
        private var INSTANCE: UrbanDictDB? = null

        fun getInstance(context: Context): UrbanDictDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, UrbanDictDB::class.java, "UrbanDictionaryDB")
                    .build().also {
                        INSTANCE = it
                    }
            }
    }
}