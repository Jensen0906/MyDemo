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
import com.may.part_10.viewmodels.UserViewModel

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"

    private lateinit var userViewModel: UserViewModel
    private lateinit var registerLauncher: ActivityResultLauncher<Intent>

    //  @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val user = User()
        binding.user = user

        getRegisterLauncher()

        binding.password.addTextChangedListener {
            if (it?.toString().isNullOrEmpty()) {
                Log.d(TAG, "onCreate: change no string")
                binding.showPassword.visibility = View.INVISIBLE
                binding.notShowPassword.visibility = View.INVISIBLE
            } else if (binding.password.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                Log.d(TAG, "onCreate: change has string need show password")
                binding.showPassword.visibility = View.VISIBLE
                binding.notShowPassword.visibility = View.INVISIBLE
            } else if (binding.password.transformationMethod == PasswordTransformationMethod.getInstance()) {
                Log.d(TAG, "onCreate: change has string need dismiss password")
                binding.showPassword.visibility = View.INVISIBLE
                binding.notShowPassword.visibility = View.VISIBLE
            } else {
                Log.d(TAG, "onCreate: just show imageview init")
                binding.showPassword.visibility = View.INVISIBLE
                binding.notShowPassword.visibility = View.VISIBLE
            }
        }

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

        binding.showPassword.setOnClickListener {
            Log.d(TAG, "onCreate: dismiss the password")
            binding.password.run {

                transformationMethod = PasswordTransformationMethod.getInstance()
                if (text != null) setSelection(text!!.length)
            }
        }

        binding.notShowPassword.setOnClickListener {
            Log.d(TAG, "onCreate: show the password")
            binding.password.run {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                if (text != null) setSelection(text!!.length)
            }
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