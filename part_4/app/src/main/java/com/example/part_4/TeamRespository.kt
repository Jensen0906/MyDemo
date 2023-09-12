package com.example.part_4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TeamRes {
    fun getTeamScore(id: Int): LiveData<Int> {
        val teamScoreLiveData = MutableLiveData<Int>()
        if (id == 7) {
            teamScoreLiveData.value = 55
        } else {
            teamScoreLiveData.value = 0
        }
        return teamScoreLiveData
    }
}