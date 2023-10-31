package com.may.part_7.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.may.part_7.application.MyApplication
import com.may.part_7.entity.User

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract val TAG: String?
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getDataBinding()
        setContentView(binding.root)
    }

    //注意这个方法不要和 全局变量 bind 的getter方法重名即写成'getBinding()'
    abstract fun getDataBinding(): T
    abstract inner class Click {
        val TAG = "Click"
        fun login(view: View, user: User) {
            Log.d(TAG, "user: $user")
        }
    }
}