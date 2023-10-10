package com.may.part_10.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.may.part_10.base.BaseActivity
import com.may.part_10.constant.BaseWorkConst.REGISTER_SUCCESS_NAME
import com.may.part_10.constant.BaseWorkConst.TO_REGISTER_FOR_RESULT
import com.may.part_10.databinding.ActivityLoginBinding
import com.may.part_10.entity.User
import com.may.part_10.viewmodels.UserViewModel

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"

    private lateinit var userViewModel: UserViewModel
    private lateinit var registerLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val user = User()
        binding.user = user

        getRegisterLauncher()

        binding.btnLogin.setOnClickListener {
            Log.d(TAG, user.toString())
            userViewModel.login(user)
        }
        userViewModel.userLiveData.observe(this) {
            if (it == null) return@observe
            if (user.username == it.username) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
        binding.tvRegister.setOnClickListener {
            registerLauncher.launch(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


    private fun getRegisterLauncher() {
        registerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
                when (it.resultCode) {
                    TO_REGISTER_FOR_RESULT -> {
                        binding.user?.run {
                            username = it.data?.getStringExtra(REGISTER_SUCCESS_NAME)
                            Log.d(TAG, "register name: $username")
                            password = "" //clear password input before
                        }
                    }
                    else -> {}
                }
            }
    }


    override fun setDataBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}