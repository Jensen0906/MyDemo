package com.may.part_10

import android.app.Application
import android.content.Context

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class App : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}