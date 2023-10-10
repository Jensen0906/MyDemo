package com.may.part_10.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.toast.Toast
import com.may.part_10.base.BaseActivity
import com.may.part_10.constant.BaseWorkConst.REGISTER_SUCCESS_NAME
import com.may.part_10.constant.BaseWorkConst.TO_REGISTER_FOR_RESULT
import com.may.part_10.databinding.ActivityRegisterBinding
import com.may.part_10.entity.User
import com.may.part_10.viewmodels.UserViewModel

/**
 * @Author Jensen
 * @Date 2023/10/09
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private val TAG = "RegisterActivity"

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val user = User()
        binding.user = user
        binding.btnRegister.setOnClickListener {
            Log.d(TAG, user.toString())
            userViewModel.register(user, binding.etPasswordAgain.text?.toString())
        }

        userViewModel.userLiveData.observe(this) {
            Toast.success(applicationContext, "register success! --${user.username}--")
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.putExtra(REGISTER_SUCCESS_NAME, it?.username)
            setResult(TO_REGISTER_FOR_RESULT, intent)
            finish()
        }
    }

    override fun setDataBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }
}