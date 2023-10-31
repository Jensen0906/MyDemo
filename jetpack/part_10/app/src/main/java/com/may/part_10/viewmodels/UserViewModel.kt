package com.may.part_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toast.Toast
import com.google.gson.Gson
import com.may.part_10.App.Companion.appContext
import com.may.part_10.R
import com.may.part_10.entity.User
import com.may.part_10.repository.UserRepository
import com.may.part_10.utils.makePassowrdEncode
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @Author Jensen
 * @Date 2023/10/07
 */

class UserViewModel : ViewModel() {
    private var _user = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _user

    private val repository = UserRepository()

    fun login(user: User?) {
        if (user == null || user.username.isNullOrEmpty() || user.password.isNullOrEmpty()) {
            Toast.warning(appContext, appContext.getString(R.string.username_or_password_empty))
            return
        }

        val requestBody =
            RequestBody.create(MediaType.parse("application/json;"), Gson().toJson(user.makePassowrdEncode()))
        viewModelScope.launch {
            repository.login(_user, requestBody)
        }
    }

    fun register(user: User?, pass2: String?) {
        if (user == null || user.username.isNullOrEmpty() || user.password.isNullOrEmpty()) {
            Toast.warning(appContext, appContext.getString(R.string.username_or_password_empty))
            return
        } else if (user.password != pass2) {
            Toast.warning(appContext, appContext.getString(R.string.twice_password_not_same))
            return
        }
        val requestBody =
            RequestBody.create(MediaType.parse("application/json;"), Gson().toJson(user.makePassowrdEncode()))
        viewModelScope.launch {
            repository.register(_user, requestBody)
        }
    }

}