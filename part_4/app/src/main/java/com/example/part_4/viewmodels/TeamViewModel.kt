package com.example.part_4.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.part_4.Team

class TeamViewModel : ViewModel() {

    private var teamData = MutableLiveData<Team>()
    val _teamData: LiveData<Team> = teamData

    fun setTeamData(team: Team) {
        teamData.value = team
    }
}