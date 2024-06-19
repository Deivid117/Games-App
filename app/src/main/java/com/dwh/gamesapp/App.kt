package com.dwh.gamesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

   /* companion object {
        lateinit var dataStorePreferences: DataStorePreferences
    }

    override fun onCreate() {
        super.onCreate()
        dataStorePreferences = DataStorePreferences(applicationContext)
    }*/
}