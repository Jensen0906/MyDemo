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

        val dataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getUser().observe(this) { dataBinding.vm = mainViewModel }
        dataBinding.btnLogin.setOnClickListener {
            val account = dataBinding.etAccount.text?.toString()!!
            val pwd = dataBinding.etPwd.text?.toString()!!
            mainViewModel.getUser().value = User(account, pwd)


            /*  单向绑定
        val dataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getUser().observe(this) { dataBinding.user = it }
        dataBinding.btnLogin.setOnClickListener {
            val account = dataBinding.etAccount.text?.toString()!!
            val pwd = dataBinding.etPwd.text?.toString()!!
            mainViewModel.getUser().value = User(account, pwd)
        }*/
        }
    }
}