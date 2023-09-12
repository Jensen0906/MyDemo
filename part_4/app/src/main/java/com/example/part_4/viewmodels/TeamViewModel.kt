package com.example.part_4.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.part_4.Team
import com.example.part_4.TeamRes

class TeamViewModel : ViewModel() {

    private var teamData = MutableLiveData<Team>()
   // val _teamData: LiveData<Team> = teamData
    val _teamData: LiveData<Int> = teamData.map {
        it.point
   }
    private var teamIdLiveData = MutableLiveData<Int>()


    fun setTeamData(team: Team) {
        teamData.value = team
    }

    fun setTeamId(teamId: Int) {
        teamIdLiveData.value = teamId
    }

    var newScore: LiveData<Int> = teamIdLiveData.switchMap {id: Int ->
        return@switchMap TeamRes().getTeamScore(id)
    }
}