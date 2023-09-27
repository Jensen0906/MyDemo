package com.may.part_8

import javax.inject.Inject

class TeamManager @Inject constructor() {

    fun getTeam() : Team {
        return Team(7, "DreamFire", "心中有梦, 肚里有火")
    }
}