package com.may.mvvm_demo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.may.mvvm_demo.entity.User

class MainViewModel : ViewModel() {

    private var user: MutableLiveData<User>? = null

    fun getUser(): MutableLiveData<User> {
        if (user == null) {
            user = MutableLiveData()
            user!!.value = User()
        }
        return user!!
    }
}