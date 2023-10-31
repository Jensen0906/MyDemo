package com.may.part_7.activities

import android.os.Bundle
import com.may.part_7.databinding.ActivityMainBinding

class MainActivity(override val TAG: String? = "MainActivity") : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun getDataBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}