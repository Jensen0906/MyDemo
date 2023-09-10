package com.example.part_3.viewmodels

import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var showInt = 5

    val listener: OnClickListener = OnClickListener {
        showInt++
    }
}