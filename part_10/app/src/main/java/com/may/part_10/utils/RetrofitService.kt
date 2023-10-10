package com.may.part_10.utils

import com.may.part_10.constant.NetWorkConst
import com.may.part_10.network.NetWorkApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(NetWorkConst.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApi(): NetWorkApi {
        return retrofit.create()
    }
}