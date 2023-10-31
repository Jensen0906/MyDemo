package com.may.part_8

import com.may.part_8.entity.Team
import javax.inject.Inject

class TeamManager @Inject constructor(val algs: ALGS) {

    fun getTeam() : Team {
        return Team(7, "DreamFire", "心中有梦, 肚里有火")
    }

    fun isALGSTeam(): Boolean = algs.isALGSTeam()
}