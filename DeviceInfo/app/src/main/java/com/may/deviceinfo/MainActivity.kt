package com.may.deviceinfo

import android.annotation.SuppressLint
import android.icu.text.ListFormatter
import android.net.wifi.SoftApConfiguration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.Arrays
import java.util.Locale

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "May"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.brand)
        text.text = Build.BRAND
        val test: MutableList<String> = ArrayList()
        test.add("测试")
        test.add("你好")
        test.add("byebye")
        Log.e(TAG, "test11: ${test.toTypedArray().contentToString()}")
        Log.e(TAG, "test22: ${test[0]} -- ${test[1]}")
        Log.e(TAG, "test33: ${ListFormatter.getInstance().format(test)}" )
    }
}