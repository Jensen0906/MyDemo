package com.example.part_4

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.part_4.viewmodels.MainViewModel

class MainManager(mainViewModel: MainViewModel) : LifecycleEventObserver {
    private val TAG = "MainManager"

    private var countDownTimer = object : CountDownTimer(mainViewModel.time, 1000) {
        override fun onTick(timeAfter: Long) {
            Log.d(TAG, "倒计时 --- ${timeAfter / 1000}s")
            mainViewModel.setResult(timeAfter / 1000)
        }

        override fun onFinish() {
            Log.d(TAG, "倒计时 结束")
        }

    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(TAG, "onStateChanged: $event")
        when (event) {
            Lifecycle.Event.ON_START -> countDownTimer.start()
            Lifecycle.Event.ON_PAUSE -> countDownTimer.cancel()
            else -> {}
        }
    }
}