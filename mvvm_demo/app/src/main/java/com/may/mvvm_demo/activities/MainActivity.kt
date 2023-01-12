package com.may.mvvm_demo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.may.mvvm_demo.R
import com.may.mvvm_demo.databinding.ActivityMainBinding
import com.may.mvvm_demo.entity.User
import com.may.mvvm_demo.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private var user = User("admin", "1234")
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        val user = User("admin", "1234")
        dataBinding.user = user
        dataBinding.btnLogin.setOnClickListener {
            user.account = dataBinding.etAccount.text?.toString()!!.trim()
            user.pwd = dataBinding.etPwd.text?.toString()!!.trim()
            user.notifyChange()
            Toast.makeText(this, " ${user.account} \n ${user.pwd} ", Toast.LENGTH_SHORT).show()
        }
//        setContentView(R.layout.activity_main)

      //  mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }
}