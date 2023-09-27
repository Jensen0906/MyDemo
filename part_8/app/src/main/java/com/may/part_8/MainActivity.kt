package com.may.part_8

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.may.part_8.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var teamManager: TeamManager

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetInfo.setOnClickListener {
            val team = teamManager.getTeam()
            binding.team = team
        }


    }
}