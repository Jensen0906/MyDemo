package com.may.mvvm_demo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.may.mvvm_demo.R
import com.may.mvvm_demo.databinding.ActivityMainBinding
import com.may.mvvm_demo.entity.User
import com.may.mvvm_demo.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}