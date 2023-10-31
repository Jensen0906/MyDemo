package com.may.part_8.network

import com.may.part_8.entity.Repositories
import com.may.part_8.entity.User
import retrofit2.http.GET

interface ApiService {
    @GET("repositories") //网络请求示例
    suspend fun getAllRepositories(): List<Repositories>

    @GET("users")
    suspend fun getAllUsers(): List<User>
}