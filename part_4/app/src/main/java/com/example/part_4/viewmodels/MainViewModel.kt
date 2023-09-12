package com.example.part_4.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var time: Long = 5000

    private var result = MutableLiveData<Long>()

    val _result: LiveData<Long> = result

    fun setResult(showLong: Long) {
        result.value = showLong
        time = showLong.times(1000)
    }

}