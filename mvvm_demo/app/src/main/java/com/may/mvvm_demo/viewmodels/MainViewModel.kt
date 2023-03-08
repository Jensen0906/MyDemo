package com.may.mvvm_demo.viewmodels

import android.accounts.Account
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.may.mvvm_demo.database.AppDatabase
import com.may.mvvm_demo.models.User

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getInstance(application).userDao()
    val users: LiveData<List<User>> = userDao.getAll()

    fun addUser(account: String, pass: String) =
}