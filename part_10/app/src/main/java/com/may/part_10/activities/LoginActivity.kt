package com.may.part_10.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.may.part_10.base.BaseActivity
import com.may.part_10.databinding.ActivityLoginBinding
import com.may.part_10.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody


class LoginActivity: BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        val job = Job()
        val user = User()
        binding.user = user
        binding.btnLogin.setOnClickListener {
            Log.d(TAG, user.toString())
            val requestBody = RequestBody.create(MediaType.parse("application/json;"), Gson().toJson(user))
            CoroutineScope(job).launch(Dispatchers.Main) {
/*                val apiResult = withContext(Dispatchers.IO) {
                    api.login(requestBody)
                }
                if (apiResult.status == 200) {
                    Toast.success(appContext, "Welcome ${user.username}")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.error(appContext, "Login Error, Try again!")
                }*/
            }

        }
    }


    override fun setDataBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}