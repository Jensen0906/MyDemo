package com.may.aes_encode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var hasEncoded = false
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnEncode = findViewById<Button>(R.id.code_text)
        val btnDecode = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.result_show)
        val needCodeString = findViewById<EditText>(R.id.et_string)
        btnEncode.setOnClickListener {
            val string = needCodeString.text.toString().trim()
            result = string.AESEncode()
            textView.run {
                text = result
            }
        }
        btnDecode.setOnClickListener {
            val string = needCodeString.text.toString().trim()
            result = string.AESDecode()
            textView.run {
                text = result
            }
        }
    }
}