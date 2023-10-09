package com.may.part_10.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.may.part_10.base.BaseActivity
import com.may.part_10.databinding.ActivityLoginBinding
import com.may.part_10.entity.User
import com.may.part_10.viewmodels.UserViewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val user = User()
        intent.getStringExtra("register_username").let {
            user.username = it
        }
        binding.user = user
        binding.btnLogin.setOnClickListener {
            Log.d(TAG, user.toString())
            userViewModel.login(user)
        }
        userViewModel.userLiveData.observe(this) {
            if (it == null) return@observe
            if (user.username == it.username) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


    override fun setDataBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}