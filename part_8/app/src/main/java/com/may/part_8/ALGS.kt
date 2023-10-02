package com.may.part_8

import android.util.Log
import javax.inject.Inject

class ALGS @Inject constructor() {

    fun isALGSTeam(): Boolean {
        Log.d("ALGS", "isALGSTeam: true")
        return true
    }
}