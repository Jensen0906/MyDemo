package com.may.part_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.may.part_9.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        //GlobalScope.launch
        Log.d(TAG, " start delay ")
        val job = GlobalScope.launch {
            delay(5000L)
            Log.d(TAG, "Thread This: ${Thread.currentThread().name}")
        }
        binding.btnStop.setOnClickListener {
            job.cancel()
        }*/
/*        //CoroutineScope(job).launch
        val job = Job()
        Log.d(TAG, "----start delay----")
        CoroutineScope(job).launch {
            delay(5000L)
            Log.d(TAG, "print this message after delay 5s (CoroutineScope)")
        }*/
/*        //必须在协程作用域中调用的async
        val job = Job()
        Log.d(TAG, "----start delay----")
        CoroutineScope(job).launch {
            val result = async {
                delay(5000L)
                return@async "print this message after delay 5s (async)"
            }.await()
            Log.d(TAG, result)
        }*/
        //

    }


}