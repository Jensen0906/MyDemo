package com.example.part_6

import android.util.Log
import android.view.View
import com.example.part_6.entity.User
import com.example.part_6.entity.UserBind

class ClickHandler {
    val TAG = "ClickHandler"

    fun click1(view: View) {
        Log.d(TAG, "click1")
    }

    fun click2(view: View, userBind: UserBind) {
        Log.d(TAG, "click2: user = $userBind")
    }
}