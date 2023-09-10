package com.example.part_4

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.part_4.databinding.ActivityMainBinding
import com.example.part_4.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val mainManager = MainManager(mainViewModel)
        lifecycle.addObserver(mainManager)
        mainViewModel._result.observe(this) {
            binding.showTime.text = "倒计时还剩下 ${it}s"
            if (it == 0L) {
                Log.d(TAG, "  OVER!! ")
                binding.showTime.text = "倒计时结束"
            }
        }
    }
}