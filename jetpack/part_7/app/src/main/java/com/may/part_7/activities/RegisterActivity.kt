package com.may.part_7.activities

import android.os.Bundle
import com.example.toast.Toast
import com.may.part_7.application.MyApplication.Companion.appContext
import com.may.part_7.database.CommonDataBase
import com.may.part_7.databinding.ActivityRegisterBinding
import com.may.part_7.entity.User

class RegisterActivity(override val TAG: String? = "RegisterActivity") :
    BaseActivity<ActivityRegisterBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.register.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val pass = binding.etPass.text.toString().trim()
            val pass2 = binding.etPass2.text.toString().trim()
            if (name.isEmpty() || pass.isEmpty()) {
                Toast.warning(appContext, " Please input username or password ")
            } else if (pass != pass2) {
                Toast.warning(appContext, " The passwords entered twice are not equal ")
            } else {
                val user = User(
                    null,
                    username = binding.etName.text.toString().trim(),
                    password = binding.etPass.text.toString().trim()
                )
                CommonDataBase.commDb.userDao.register(user)
                Toast.success(appContext, " Register success $name")
                resetEditText()
            }
        }
    }

    private fun resetEditText() {
        binding.let {
            it.etName.text?.clear()
            it.etPass.text?.clear()
            it.etPass2.text?.clear()
            it.etName.clearFocus()
            it.etPass.clearFocus()
            it.etPass2.clearFocus()
        }
    }

    override fun getDataBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }
}