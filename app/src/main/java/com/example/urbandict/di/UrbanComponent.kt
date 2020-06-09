package com.example.urbandict.di

import com.example.urbandict.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DBModule::class, NetworkModule::class])
interface UrbanComponent {
    fun inject(mainActivity: MainActivity)
}