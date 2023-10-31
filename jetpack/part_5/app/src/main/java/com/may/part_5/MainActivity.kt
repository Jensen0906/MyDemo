package com.may.part_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.may.part_5.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() /*AppCompatActivity()*//* Fragment() */{

  //  lateinit var activityMainBinding: ActivityMainBinding
  //  private var fragmentMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.tvHelloWorld.text = "Hello World!!!!!!"
/*        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.tvHelloWorld.text = "Hello World!!!"*/
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    /*    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            fragmentMainBinding = ActivityMainBinding.inflate(inflater, container, false)
            return fragmentMainBinding?.root
        }*/
}