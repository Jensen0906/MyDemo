package com.example.part_6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.part_6.databinding.ActivityMainBinding
import com.example.part_6.entity.User
import com.example.part_6.entity.UserBind

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*        //单向绑定
        binding.submitInfo.setOnClickListener {
            val id = binding.etId.text.toString().trim()
            val name = binding.etUsername.text.toString().trim()
            val user = User(id, name)
            binding.user = user
        }*/
        //双向绑定
        val userBind = UserBind()
        binding.userBind = userBind
        //click bind
        binding.clickHandler = ClickHandler()
    }
}