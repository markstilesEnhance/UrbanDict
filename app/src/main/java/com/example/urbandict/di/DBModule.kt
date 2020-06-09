package com.example.urbandict.di

import android.content.Context
import androidx.room.Room
import com.example.urbandict.model.db.UrbanDictDAO
import com.example.urbandict.model.db.UrbanDictDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule(context: Context) {

    private val database: UrbanDictDB = Room.databaseBuilder(context, UrbanDictDB::class.java, "UrbanDictionaryDB").build()

    @Singleton
    @Provides
    fun provideDatabase(): UrbanDictDB = database

    @Singleton
    @Provides
    fun provideDao(db: UrbanDictDB): UrbanDictDAO = db.defsDao()

}