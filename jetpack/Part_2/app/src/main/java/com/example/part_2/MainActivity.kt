package com.example.part_2

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.part_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dialog = AutoDialog(this)
      //  val myLifecycleObserver = MainLifeObserver()
        lifecycle.addObserver(dialog)
        binding.show.setOnClickListener {
            dialog.show()
        }
    }

    class AutoDialog(context: Context) : Dialog(context), LifecycleEventObserver{
        private val TAG = "Dialog"
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when (event) {
                Lifecycle.Event.ON_DESTROY ->
                    if (isShowing) {
                        Log.d(TAG, "onStateChanged: dismiss")
                        dismiss()
                    }
                else -> {}
            }
        }

    }

}

/*class MainActivity : Activity(), LifecycleOwner {

    lateinit var binding: ActivityMainBinding

    private val lifecycleRegistry = LifecycleRegistry(this@MainActivity)

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myLifecycleObserver = MainLifeObserver()
        lifecycle.addObserver(myLifecycleObserver)
    }

}*/
