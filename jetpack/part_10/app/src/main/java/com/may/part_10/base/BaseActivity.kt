package com.may.part_10.base

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

abstract class BaseActivity<VDB : ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setDataBinding()
        setContentView(binding.root)
    }

    protected abstract fun setDataBinding() : VDB
}