package com.example.camerademo

import android.app.Application
import android.content.Context

class CameraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}