package com.example.part_3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.part_3.databinding.ActivityMainBinding
import com.example.part_3.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private var showInt = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*        binding.show.text = showInt.toString()
        Log.d(TAG, "show number, now is $showInt")
        binding.add.setOnClickListener {
            showInt++
            binding.show.text = showInt.toString()
            Log.d(TAG, "add number, now is $showInt")
        }*/

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.show.text = mainViewModel.showInt.toString()
        Log.d(TAG, "show number, now is ${mainViewModel.showInt}")
        binding.add.setOnClickListener {
            mainViewModel.showInt++
            binding.show.text = mainViewModel.showInt.toString()
            Log.d(TAG, "add number, now is ${mainViewModel.showInt}")
        }
    }

}