package com.may.part_9.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users") fun queryDataLoser(): Call<List<UserBean>>

    @GET("users") suspend fun queryData(): List<UserBean>
}