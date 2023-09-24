package com.may.part_7.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.may.part_7.database.CommonDataBase


class MyApplication : Application() {
    private val TAG = "MyApplication"

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        /*var commonDataBase =
            Room.databaseBuilder(context = this, CommonDataBase::class.java, "part77.db").build()*/
        Log.d(TAG, "DataBase file path: ${appContext.getDatabasePath("part7.db").absolutePath}")
    }
}