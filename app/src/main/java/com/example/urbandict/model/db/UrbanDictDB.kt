package com.example.urbandict.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.urbandict.model.DefinitionItem

@Database(entities = [DefinitionItem::class], version = 3, exportSchema = false)
abstract class UrbanDictDB: RoomDatabase() {

    abstract fun defsDao(): UrbanDictDAO

}