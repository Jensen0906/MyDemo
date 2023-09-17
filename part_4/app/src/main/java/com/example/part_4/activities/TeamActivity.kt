package com.example.part_4.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.part_4.Team
import com.example.part_4.databinding.ActivityTeamBinding
import com.example.part_4.viewmodels.TeamViewModel

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        teamViewModel = ViewModelProvider(this)[TeamViewModel::class.java]
        val team1 = Team("DreamFire", 7,55)
        teamViewModel.setTeamData(team1)

        teamViewModel._teamData.observe(this){
            binding.teamInfo.text = "DF is no.7, score: $it"
        }
        teamViewModel.newPoint.observe(this) {
            binding.scoreShow.text = "score: $it"
        }
        binding.submit.setOnClickListener {
            teamViewModel.setTeamId(binding.inputText.text.toString().toInt())
        }
    }
}