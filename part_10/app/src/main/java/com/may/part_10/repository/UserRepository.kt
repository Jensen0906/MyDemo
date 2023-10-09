package com.may.part_10.repository

import androidx.lifecycle.MutableLiveData
import com.may.part_10.base.BaseRepository
import com.may.part_10.entity.User
import com.may.part_10.utils.RetrofitService
import okhttp3.RequestBody

class UserRepository : BaseRepository() {

    private val api = RetrofitService.getApi()

    suspend fun login(user: MutableLiveData<User?>, requestBody: RequestBody) {
        execute({ api.login(requestBody) }, user)
    }

    suspend fun register(user: MutableLiveData<User?>, requestBody: RequestBody) {
        execute({ api.register(requestBody) }, user)
    }
}