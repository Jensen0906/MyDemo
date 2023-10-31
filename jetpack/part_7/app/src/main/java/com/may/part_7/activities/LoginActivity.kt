package com.may.part_7.activities

import android.content.Intent
import android.os.Bundle
import com.example.toast.Toast
import com.may.part_7.application.MyApplication.Companion.appContext
import com.may.part_7.database.CommonDataBase
import com.may.part_7.databinding.ActivityLoginBinding
import com.may.part_7.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity(override val TAG: String? = "LoginActivity") :
    BaseActivity<ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.login.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val pass = binding.etPass.text.toString().trim()
          //  loginInThread(name, pass)
            loginInUIThread(name, pass)
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    /**
     * run login in UI thread, need DataBase allow run in Main thread
     * @author jensen
     */
    private fun loginInUIThread(name: String, pass: String) {
        if (name.isEmpty() || pass.isEmpty()) {
            Toast.warning(appContext, " Please input username or password ")
        } else if (CommonDataBase.commDb.userDao.login(name, pass) != null) {
            Toast.success(appContext, " Welcome! $name ")
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        } else {
            Toast.error(appContext, " Not Found ")
        }
    }
    /**
     * if DataBase not allow run in Main thread,use this Method
     * @author jensen
     */
    private fun loginInThread(name: String, pass: String) {
        if (name.isEmpty() || pass.isEmpty()) {
            Toast.warning(appContext, " Please input username or password ")
        } else {
            CoroutineScope(Job()).launch(Dispatchers.Main) {
                val userResult = login(name, pass)
                if (userResult != null) {
                    Toast.success(appContext, " Welcome! $name ")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.error(appContext, " Not Found ")
                }
            }
        }
    }

    private suspend fun login(name: String, pass: String): User? {
        return withContext(Dispatchers.IO) {
            return@withContext CommonDataBase.commDb.userDao.login(name, pass)
        }
    }
    override fun getDataBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}