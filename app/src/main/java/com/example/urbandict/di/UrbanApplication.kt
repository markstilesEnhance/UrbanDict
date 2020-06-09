package com.example.urbandict.di

import android.app.Application

class UrbanApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        urbanComponent = DaggerUrbanComponent.builder()
            .dBModule(DBModule(this.applicationContext))
            .networkModule(NetworkModule())
            .build()
    }

    companion object{
        lateinit var urbanComponent: UrbanComponent
    }
}