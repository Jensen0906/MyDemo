package com.example.part_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.part_6.databinding.ActivityMainBinding
import com.example.part_6.entity.User
import com.example.part_6.entity.UserBind

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.submitInfo.setOnClickListener {
            val id = binding.etId.text.toString().trim()
            val name = binding.etUsername.text.toString().trim()
            val user = User(id, name)
            binding.user = user
        }
/*        val user = getUserInfo()
        binding.user = user*/
    }

    private fun getUserInfo(): UserBind {
        val name = binding.etUsername.text.toString().trim()
        val id = binding.etId.text.toString().trim()
        val userBind = UserBind()
        userBind.id = id
        userBind.name = name
        return userBind
    }
}