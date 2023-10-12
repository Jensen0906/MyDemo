package com.may.part_10.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.may.part_10.base.BaseActivity
import com.may.part_10.constant.BaseWorkConst.REGISTER_SUCCESS_NAME
import com.may.part_10.constant.BaseWorkConst.TO_REGISTER_FOR_RESULT
import com.may.part_10.databinding.ActivityLoginBinding
import com.may.part_10.entity.User
import com.may.part_10.utils.AESEncode
import com.may.part_10.utils.changePassShow
import com.may.part_10.utils.changeShowType
import com.may.part_10.viewmodels.UserViewModel

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"

    private lateinit var userViewModel: UserViewModel
    private lateinit var registerLauncher: ActivityResultLauncher<Intent>

    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUser = User()
        binding.user = mUser

        getRegisterLauncher()

        userViewModel.userLiveData.observe(this) {
            if (it == null) return@observe
            if (mUser.username == it.username) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        initClick()

        binding.password.changeShowType(binding.showPassword, binding.notShowPassword)
    }

    /**
     * init all click listener
     * */
    private fun initClick() {
        binding.btnLogin.setOnClickListener {
            Log.d(TAG, mUser.toString())
            userViewModel.login(mUser)
        }
        binding.tvRegister.setOnClickListener {
            registerLauncher.launch(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        binding.showPassword.changePassShow(binding.password, needShow = false)
        binding.notShowPassword.changePassShow(password = binding.password, needShow = true)
    }

    /**
     * register the 'ActivityResultLauncher<Intent>' to get username
     * if user register success
     * */
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