package com.example.part_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.part_6.databinding.ActivityMainBinding
import com.example.part_6.entity.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitInfo.setOnClickListener {
            val user = getUserInfo()
            binding.user = user
        }
    }

    private fun getUserInfo(): User {
        val name = binding.etUsername.text.toString().trim()
        val id = binding.etId.text.toString().trim()
        return User(id, name)
    }
}