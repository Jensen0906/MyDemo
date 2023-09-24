package com.may.part_7.tools

import android.util.Log
import android.view.View
import com.may.part_7.entity.User

class ClickHandler {
    val TAG = "ClickHandler"

    fun login(view: View, user: User) {
        Log.d(TAG, "user:${user.username}, ${user.password}")
    }
}