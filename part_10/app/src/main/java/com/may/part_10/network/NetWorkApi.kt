package com.may.part_10.network

import com.may.part_10.entity.Book
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetWorkApi {

    @POST("user/login")
    suspend fun login(@Body body: RequestBody): ApiResult<Any?>

    @GET("book/find_all_books")
    suspend fun getAllBooks(): ApiResult<ArrayList<Book>?>
}