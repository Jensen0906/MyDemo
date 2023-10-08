package com.may.kotlin_extension_function

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv_show)

/*        tv.also {
            it.text = "also"
        }.let {
            it.textSize = 23f
        }*/

        tv.apply {
            text = "apply"
        }.run {
            textSize = 26f
        }
    }
}