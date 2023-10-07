package com.may.part_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toast.Toast
import com.google.gson.Gson
import com.may.part_10.App.Companion.appContext
import com.may.part_10.entity.User
import com.may.part_10.repository.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class UserViewModel : ViewModel() {
    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val repository = UserRepository()

    fun login(user: User?) {
        if (user == null || user.username.isNullOrEmpty() || user.password.isNullOrEmpty()) {
            Toast.warning(appContext, "data input error!")
            return
        }
        val requestBody =
            RequestBody.create(MediaType.parse("application/json;"), Gson().toJson(user))
        viewModelScope.launch {
            repository.login(_user, requestBody)
        }
    }
}